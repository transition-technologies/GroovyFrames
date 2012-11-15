package frames

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.frames.EdgeFrame
import com.tinkerpop.frames.Incidence
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.PropertyExpression

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
class IncidenceGenerator {
    def generate(classNode, field) {
        def name = field.name
        def type = field.type

        def annotations = field.getAnnotations(ClassHelper.makeCached(FrameElement.class))
        def directionExpression = annotations ? annotations[0].getMember("value") : null

        def propertyAnnotation = new AnnotationNode(ClassHelper.makeCached(Incidence.class))
        propertyAnnotation.addMember("label", new ConstantExpression(name))
        def defaultDirection = new PropertyExpression(new ClassExpression(ClassHelper.makeCached(Direction.class)),
                ClassHelper.make(FrameElement.class).getMethod("value", Parameter.EMPTY_ARRAY).code.expression)
        propertyAnnotation.addMember("direction", directionExpression ?: defaultDirection)

        def getter = PropertyGenerator.getter(name, type)
        getter.addAnnotation(propertyAnnotation)
        classNode.addMethod(getter)

        def setter = PropertyGenerator.setter(name, type)
        setter.addAnnotation(propertyAnnotation)
        classNode.addMethod(setter)
    }

    def isValid(field) {
        boolean process = field.getAnnotations(ClassHelper.makeCached(FrameElement.class))
        process = process && field.type.interfaces.contains(ClassHelper.makeCached(EdgeFrame.class))
        return process
    }
}
