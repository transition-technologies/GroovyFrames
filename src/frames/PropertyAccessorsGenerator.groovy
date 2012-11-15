package frames

import com.tinkerpop.frames.Property
import org.codehaus.groovy.ast.builder.AstBuilder

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT
import static org.objectweb.asm.Opcodes.ACC_PUBLIC

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
        def clazz = field.type.typeClass
        def ast = new AstBuilder().buildFromSpec {
            method("get${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, clazz) {
                parameters {}
                exceptions {}
                block {}
                annotations {
                    annotation Property.class, {
                        member "value", {
                            constant(name)
                        }
                    }
                }
            }

            method("set${name.capitalize()}", ACC_PUBLIC | ACC_ABSTRACT, Void.TYPE) {
                parameters {parameter([(name): clazz])}
                exceptions {}
                block {}
                annotations {
                    annotation Property.class, {
                        member "value", {
                            constant(name)
                        }
                    }
                }
            }
        }

        ast.each {
            classNode.addMethod(it)
        }
    }
}
