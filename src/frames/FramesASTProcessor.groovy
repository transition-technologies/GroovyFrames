package frames

import com.tinkerpop.frames.Property
import groovy.util.logging.Slf4j
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT
import static org.objectweb.asm.Opcodes.ACC_PUBLIC

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
@Slf4j
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class FramesASTProcessor implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        nodes.grep { it instanceof ClassNode }.each { node ->
            println "Processing node: $node"
            node.fields.each { field ->
                println "Processing field: ${field.name}"
                def accessors = generateAccessors(field.type, field.name)
                accessors.each {
                    node.addMethod(it)
                }
                node.fields -= field
            }
        }
    }

    private def generateAccessors(type, name) {
        def ast = new AstBuilder().buildFromSpec {
            method("get${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, type.typeClass) {
                parameters {}
                exceptions {}
                block {}
                annotations {
                    annotation Property.class, {
                        member "value", {
                            constant(name)
                        }
                    }
                }
            }

            method("set${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, Void.TYPE) {
                parameters {parameter([(name): type.typeClass])}
                exceptions {}
                block {}
                annotations {
                    annotation Property.class, {
                        member "value", {
                            constant(name)
                        }
                    }
                }
            }
        }

        return ast
    }
}
