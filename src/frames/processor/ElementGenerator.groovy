package frames.processor

import org.codehaus.groovy.ast.ClassHelper
import frames.FrameElement

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Mixin(GeneratorMethods)
class ElementGenerator {
    def generate(classNode, field) {
        def name = field.name
        def type = field.type

        def propertyAnnotation = createConnectionAnnotation(field)

        addProperty(classNode, name, type, propertyAnnotation)
    }

    def isValid(field) {
        use(FieldValidationCategory) {
            return field.isElement() && (field.isEdge() || field.isVertex())
        }
    }
}
