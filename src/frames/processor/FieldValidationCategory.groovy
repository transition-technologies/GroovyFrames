package frames.processor

import com.tinkerpop.frames.EdgeFrame
import com.tinkerpop.frames.VertexFrame
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.FieldNode
import frames.FrameElement
import frames.FrameProperty

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
class FieldValidationCategory {

    static boolean isElement(FieldNode field) {
        field.getAnnotations(ClassHelper.makeCached(FrameElement.class))
    }

    static boolean isEdge(FieldNode field) {
        field.type.interfaces.contains(ClassHelper.makeCached(EdgeFrame.class))
    }

    static boolean isVertex(FieldNode field) {
        field.type.interfaces.contains(ClassHelper.makeCached(VertexFrame.class))
    }

    static boolean isEdgeCollection(FieldNode field) {
        return isCollectionOf(field, EdgeFrame.class)

    }

    static boolean isVertexCollection(FieldNode field) {
        return isCollectionOf(field, VertexFrame.class)

    }

    private static isCollectionOf(FieldNode field, Class type) {
        return isCollection(field) &&
                field.type.genericsTypes?.size() == 1 &&
                field.type.genericsTypes[0].type.interfaces.contains(ClassHelper.makeCached(type))
    }

    static boolean isProperty(FieldNode field) {
        field.getAnnotations(ClassHelper.makeCached(FrameProperty.class))
    }

    static boolean isCollection(FieldNode field) {
        field.type == ClassHelper.makeCached(Iterable.class)
    }
}
