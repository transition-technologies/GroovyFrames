package frames.processor

import groovy.util.logging.Slf4j
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode

import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
@Slf4j
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class FramesASTProcessor implements ASTTransformation {


    def generators = [new PropertyGenerator(), new ElementGenerator(), new ElementListGenerator()]

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        nodes.grep { it instanceof ClassNode }.each { node ->
            log.debug "Processing node: $node"
            node.fields?.each { field ->
                log.debug "Processing field: ${field.name}: ${field.annotations}"
                generators.each {generator ->
                    if (generator.isValid(field)) {
                        generator.generate(node, field)
                    }
                }
                node.fields -= field
            }
        }
    }
}
