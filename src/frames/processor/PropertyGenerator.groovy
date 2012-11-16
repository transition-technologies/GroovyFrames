package frames.processor

import com.tinkerpop.frames.Property
import org.codehaus.groovy.ast.expr.ConstantExpression

import org.codehaus.groovy.ast.*

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Mixin(GeneratorMethods)
class PropertyGenerator {

    def generate(classNode, field) {
        def name = field.name
        def type = field.type

        def propertyAnnotation = new AnnotationNode(ClassHelper.makeCached(Property.class))
        propertyAnnotation.addMember("value", new ConstantExpression(name))

        addProperty(classNode, name, type, propertyAnnotation)
    }

    def isValid(field) {
        use(FieldValidationCategory) {
            return field.isProperty()
        }
    }
}
