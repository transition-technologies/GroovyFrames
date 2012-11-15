package frames

import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import com.tinkerpop.frames.Incidence
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.ClassExpression
import com.tinkerpop.blueprints.Direction
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.MethodNode

import static org.objectweb.asm.Opcodes.ACC_PUBLIC
import static org.objectweb.asm.Opcodes.ACC_ABSTRACT
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.stmt.EmptyStatement

import static org.objectweb.asm.Opcodes.ACC_PUBLIC
import static org.objectweb.asm.Opcodes.ACC_ABSTRACT

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
class GeneratorMethods {

    def createConectionAnnotation(fieldName, elementAnnotation) {
        def directionExpression = elementAnnotation.getMember("value")
        def labelExpression = elementAnnotation.getMember("label")

        def propertyAnnotation = new AnnotationNode(ClassHelper.makeCached(Incidence.class))
        propertyAnnotation.addMember("label", labelExpression?.value ? labelExpression : new ConstantExpression(fieldName))
        def defaultDirection = new PropertyExpression(new ClassExpression(ClassHelper.makeCached(Direction.class)),
                ClassHelper.make(FrameElement.class).getMethod("value", Parameter.EMPTY_ARRAY).code.expression)
        propertyAnnotation.addMember("direction", directionExpression ?: defaultDirection)

        return propertyAnnotation
    }

    def getter(name, type) {
        new MethodNode("get${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, type,
                Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE)
    }

    def setter(name, type) {
        new MethodNode("set${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, ClassHelper.VOID_TYPE,
                [new Parameter(type, name)] as Parameter[], ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE)
    }
}
