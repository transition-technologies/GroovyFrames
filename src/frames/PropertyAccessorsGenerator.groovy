package frames

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.stmt.EmptyStatement

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT
import static org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import com.tinkerpop.frames.Property
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.ClassHelper

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
class PropertyAccessorsGenerator {

    def field
    def classNode

    def generate() {
        def name = field.name
        def type = field.type

        def annotation = new AnnotationNode(new ClassNode(Property.class))
        annotation.addMember("value", new ConstantExpression(name))

        def getter = new MethodNode("get${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, type,
                Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, new EmptyStatement())
        getter.addAnnotation(annotation)

        def setter = new MethodNode("set${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, ClassHelper.VOID_TYPE,
                [new Parameter(type, name)] as Parameter[], ClassNode.EMPTY_ARRAY, new EmptyStatement())
        setter.addAnnotation(annotation)

        classNode.addMethod(getter)
        classNode.addMethod(setter)
    }
}
