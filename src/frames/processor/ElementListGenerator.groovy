package frames.processor

import org.codehaus.groovy.ast.ClassHelper
import frames.FrameElement

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
@Mixin(GeneratorMethods)
class ElementListGenerator {
    def generate(classNode, field) {
        def name = field.name.endsWith('s') ? field.name[0..-2] : field.name
        def type = field.type

        def propertyAnnotation = createConnectionAnnotation(field)

        addProperty(classNode, field.name, type, propertyAnnotation)
        addCollectionProperties(classNode, name, type, propertyAnnotation)
    }

    def isValid(field) {
        use(FieldValidationCategory) {
            return field.isElement() && (field.isEdgeCollection() || field.isVertexCollection())
        }
    }
}
