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
        expect:
        def getter = FramedVertex.class.getDeclaredMethod(method)
        getter.getAnnotation(annotation)

        where:
        method           | annotation
        "getEdge"        | Incidence.class
        "getEdges"       | Incidence.class
        "getBothEdges"   | Incidence.class
        "getVertex"      | Adjacency.class
        "getVertexs"     | Adjacency.class
        "getBothVertexs" | Adjacency.class
    }

    @Test
    void shouldHaveRightGetterDirection() {
        expect:
        def getter = FramedVertex.class.getDeclaredMethod(method)
        getter.getAnnotation(annotation).direction() == direction

        where:
        method           | annotation      | direction
        "getEdge"        | Incidence.class | Direction.OUT
        "getEdgeIn"      | Incidence.class | Direction.IN
        "getEdgeOut"     | Incidence.class | Direction.OUT
        "getEdgeBoth"    | Incidence.class | Direction.BOTH
        "getEdges"       | Incidence.class | Direction.OUT
        "getInEdges"     | Incidence.class | Direction.IN
        "getOutEdges"    | Incidence.class | Direction.OUT
        "getBothEdges"   | Incidence.class | Direction.BOTH
        "getVertex"      | Adjacency.class | Direction.OUT
        "getVertexIn"    | Adjacency.class | Direction.IN
        "getVertexOut"   | Adjacency.class | Direction.OUT
        "getVertexBoth"  | Adjacency.class | Direction.BOTH
        "getVertexs"     | Adjacency.class | Direction.OUT
        "getInVertexs"   | Adjacency.class | Direction.IN
        "getOutVertexs"  | Adjacency.class | Direction.OUT
        "getBothVertexs" | Adjacency.class | Direction.BOTH
    }

    @Test
    void shouldHaveRightSetterDirection() {
        expect:
        def getter = FramedVertex.class.getDeclaredMethod(method, type)
        getter.getAnnotation(annotationType).direction() == direction

        where:
        method          | direction      | type         | annotationType
        "setEdge"       | Direction.OUT  | Edge.class   | Incidence.class
        "setEdgeOut"    | Direction.OUT  | Edge.class   | Incidence.class
        "setEdgeIn"     | Direction.IN   | Edge.class   | Incidence.class
        "setEdgeBoth"   | Direction.BOTH | Edge.class   | Incidence.class
        "setVertex"     | Direction.OUT  | Vertex.class | Adjacency.class
        "setVertexOut"  | Direction.OUT  | Vertex.class | Adjacency.class
        "setVertexIn"   | Direction.IN   | Vertex.class | Adjacency.class
        "setVertexBoth" | Direction.BOTH | Vertex.class | Adjacency.class
    }

    @Test
    void shouldHaveRightEdgeListSetterDirection() {
        expect:
        def setter = FramedVertex.class.getDeclaredMethod(method, Iterable.class)
        setter.getAnnotation(annotationType).direction() == direction

        where:
        method             | direction      | annotationType
        "setEdges"         | Direction.OUT  | Incidence.class
        "setInEdges"       | Direction.IN   | Incidence.class
        "setOutEdges"      | Direction.OUT  | Incidence.class
        "setBothEdges"     | Direction.BOTH | Incidence.class
        "addEdge"          | Direction.OUT  | Incidence.class
        "addInEdge"        | Direction.IN   | Incidence.class
        "addOutEdge"       | Direction.OUT  | Incidence.class
        "addBothEdge"      | Direction.BOTH | Incidence.class
        "removeEdge"       | Direction.OUT  | Incidence.class
        "removeInEdge"     | Direction.IN   | Incidence.class
        "removeOutEdge"    | Direction.OUT  | Incidence.class
        "removeBothEdge"   | Direction.BOTH | Incidence.class
        "setVertexs"       | Direction.OUT  | Adjacency.class
        "setInVertexs"     | Direction.IN   | Adjacency.class
        "setOutVertexs"    | Direction.OUT  | Adjacency.class
        "setBothVertexs"   | Direction.BOTH | Adjacency.class
        "addVertex"        | Direction.OUT  | Adjacency.class
        "addInVertex"      | Direction.IN   | Adjacency.class
        "addOutVertex"     | Direction.OUT  | Adjacency.class
        "addBothVertex"    | Direction.BOTH | Adjacency.class
        "removeVertex"     | Direction.OUT  | Adjacency.class
        "removeInVertex"   | Direction.IN   | Adjacency.class
        "removeOutVertex"  | Direction.OUT  | Adjacency.class
        "removeBothVertex" | Direction.BOTH | Adjacency.class
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
    Vertex vertex

    @FrameElement(Direction.OUT)
    Vertex vertexOut

    @FrameElement(Direction.IN)
    Vertex vertexIn

    @FrameElement(Direction.BOTH)
    Vertex vertexBoth

    @FrameElement
    Iterable<Edge> edges

    @FrameElement(Direction.OUT)
    Iterable<Edge> outEdges

    @FrameElement(Direction.IN)
    Iterable<Edge> inEdges

    @FrameElement(Direction.BOTH)
    Iterable<Edge> bothEdges

    @FrameElement
    Iterable<Vertex> vertexs

    @FrameElement(Direction.OUT)
    Iterable<Vertex> outVertexs //not a very nice name :)

    @FrameElement(Direction.IN)
    Iterable<Vertex> inVertexs

    @FrameElement(Direction.BOTH)
    Iterable<Vertex> bothVertexs
}

interface PlainVertex {}

interface Edge extends EdgeFrame {}

interface Vertex extends VertexFrame {}
