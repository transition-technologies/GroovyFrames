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
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class FramesASTProcessor implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        nodes.grep { it instanceof ClassNode }.each { node ->
            println "Processing node: $node"
            node.fields.each { field ->
                println "Processing field: ${field.name}: ${field.annotations}"
                def generator = new PropertyAccessorsGenerator(classNode: node, field: field)
                generator.generate()
                node.fields -= field
            }
        }
    }

    private def createGenerator(field) {
        def generator = new PropertyAccessorsGenerator(classNode: node, field: field)
    }
}
