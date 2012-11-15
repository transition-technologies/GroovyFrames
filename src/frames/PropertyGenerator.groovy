package frames

import com.tinkerpop.frames.Property
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.stmt.EmptyStatement
import org.codehaus.groovy.ast.*

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT
import static org.objectweb.asm.Opcodes.ACC_PUBLIC

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

        def getter = getter(name, type)
        getter.addAnnotation(propertyAnnotation)
        classNode.addMethod(getter)

        def setter = setter(name, type)
        setter.addAnnotation(propertyAnnotation)


        classNode.addMethod(setter)

    }

    def isValid(field) {
        field.getAnnotations(new ClassNode(FrameProperty.class))
    }
}
