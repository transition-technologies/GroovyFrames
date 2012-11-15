import com.tinkerpop.frames.Adjacency
import com.tinkerpop.frames.VertexFrame
import frames.Frame
import frames.FrameProperty
import org.junit.Test
import com.tinkerpop.frames.Incidence
import com.tinkerpop.frames.EdgeFrame
import frames.FrameElement
import com.tinkerpop.blueprints.Direction
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: piechutm
 * Date: 11/15/12
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
class GraphElementPropertyTest extends Specification {

    @Test
    void shouldNotCreateGraphElementForPlainVertex() {
        given:
        def plainGetter = FramedVertex.class.getDeclaredMethod("getPlainVertex")

        expect:
        !plainGetter.getAnnotation(Adjacency.class)
        !plainGetter.getAnnotation(Incidence.class)
    }

    @Test
    void shouldCreateIncidenceForEdgeFrameField() {
        given:
        def getter = FramedVertex.class.getDeclaredMethod("getEdge")

        expect:
        getter.getAnnotation(Incidence.class)
    }

    @Test
    void shouldHaveRightDirection() {
        expect:
        def getter = FramedVertex.class.getDeclaredMethod(method)
        getter.getAnnotation(Incidence.class).direction() == direction

        where:
        method        | direction
        "getEdge"     | Direction.OUT
        "getEdgeOut"  | Direction.OUT
//        "getEdges"    | Direction.OUT
        "getEdgeIn"   | Direction.IN
        "getEdgeBoth" | Direction.BOTH
    }
}

@Frame
interface FramedVertex extends VertexFrame {
    @FrameProperty
    PlainVertex plainVertex

    @FrameElement
    Edge edge

    @FrameElement(Direction.OUT)
    Edge edgeOut

    @FrameElement(Direction.IN)
    Edge edgeIn

    @FrameElement(Direction.BOTH)
    Edge edgeBoth

    @FrameElement
    Iterable<Edge> edges
}

interface PlainVertex {

}

interface Edge extends EdgeFrame {

}
