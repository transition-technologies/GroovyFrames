package frames.processor

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
import frames.FrameElement
import com.tinkerpop.frames.Adjacency

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
class GeneratorMethods {

    def createConnectionAnnotation(field) {
        use(FieldValidationCategory) {
            def fieldName = field.isCollection() ? (field.name.endsWith('s') ? field.name[0..-2] : field.name) : field.name

            def elementAnnotation = field.getAnnotations(ClassHelper.makeCached(FrameElement.class))[0]

            def directionExpression = elementAnnotation.getMember("value")
            def labelExpression = elementAnnotation.getMember("label")

            def annotationClass = field.isVertex() || field.isVertexCollection() ? Adjacency.class : Incidence.class

            def propertyAnnotation = new AnnotationNode(ClassHelper.makeCached(annotationClass))
            propertyAnnotation.addMember("label", labelExpression?.value ? labelExpression : new ConstantExpression(fieldName))
            def defaultDirection = new PropertyExpression(new ClassExpression(ClassHelper.makeCached(Direction.class)),
                    ClassHelper.make(FrameElement.class).getMethod("value", Parameter.EMPTY_ARRAY).code.expression)
            propertyAnnotation.addMember("direction", directionExpression ?: defaultDirection)
            return propertyAnnotation
        }
    }

    def getter(name, type) {
        new MethodNode("get${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, type,
                Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE)
    }

    def setter(name, type) {
        mutator("set", name, type)
    }

    def adder(name, type) {
        mutator("add", name, type)
    }

    def remover(name, type) {
        mutator("remove", name, type)
    }

    def mutator(prefix, name, type) {
        new MethodNode(prefix + name.capitalize(), ACC_PUBLIC | ACC_ABSTRACT, ClassHelper.VOID_TYPE,
                [new Parameter(type, name)] as Parameter[], ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE)
    }

    void addProperty(classNode, name, type, annotation) {
        def getter = getter(name, type)
        getter.addAnnotation(annotation)
        classNode.addMethod(getter)

        def setter = setter(name, type)
        setter.addAnnotation(annotation)
        classNode.addMethod(setter)
    }

    void addCollectionProperties(classNode, name, type, annotation) {
        def adder = adder(name, type)
        adder.addAnnotation(annotation)
        classNode.addMethod(adder)

        def remover = remover(name, type)
        remover.addAnnotation(annotation)
        classNode.addMethod(remover)
    }
}
