package frames

import com.tinkerpop.frames.EdgeFrame
import org.codehaus.groovy.ast.ClassHelper

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Mixin(GeneratorMethods)
class IncidenceGenerator {
    def generate(classNode, field) {
        def name = field.name
        def type = field.type

        def annotations = field.getAnnotations(ClassHelper.makeCached(FrameElement.class))
        def propertyAnnotation = createConectionAnnotation(name, annotations[0])

        def getter = getter(name, type)
        getter.addAnnotation(propertyAnnotation)
        classNode.addMethod(getter)

        def setter = setter(name, type)
        setter.addAnnotation(propertyAnnotation)
        classNode.addMethod(setter)
    }

    def isValid(field) {
        boolean process = field.getAnnotations(ClassHelper.makeCached(FrameElement.class))
        process = process && field.type.interfaces.contains(ClassHelper.makeCached(EdgeFrame.class))
        return process
    }
}
