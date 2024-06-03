package me.niculicicris.visualiser.shader;

import me.niculicicris.visualiser.component.position.CameraPosition;
import me.niculicicris.visualiser.component.position.Position;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class ArrowShader extends Shader {
    private static final String VERTEX_SHADER_PATH = "shader/arrow/ArrowVertexShader.glsl";
    private static final String FRAGMENT_SHADER_PATH = "shader/arrow/ArrowFragmentShader.glsl";

    private final CameraPosition cameraPosition;
    private Position position;

    private int vaoId;
    private int vboId;
    private int eboId;

    public ArrowShader(CameraPosition cameraPosition, Position position) {
        super(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);

        this.cameraPosition = cameraPosition;
        this.position = position;
    }

    @Override
    protected void createVertexArrayObject() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
    }

    @Override
    protected void createVertexBufferObject() {
        generateVertexBufferObject();
        setVertexBufferData();
    }

    private void generateVertexBufferObject() {
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
    }

    private void setVertexBufferData() {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(24);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
    }

    @Override
    protected void createElementBufferObject() {
        generateElementBufferObject();
        setElementBufferData();
    }

    private void generateElementBufferObject() {
        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
    }

    private void setElementBufferData() {
        IntBuffer elementData = createElementBufferData();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementData, GL_DYNAMIC_DRAW);
    }

    private IntBuffer createElementBufferData() {
        IntBuffer elementData = BufferUtils.createIntBuffer(9);
        int[] elementArray = new int[] {
                0, 1, 2,
                0, 2, 3,
                4, 6, 5
        };

        elementData.put(elementArray).flip();
        return elementData;
    }

    @Override
    protected void createAttributePointers() {
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }

    @Override
    public void use() {
        updateVertexBufferData();
        prepare();
        draw();
        cleanup();
    }

    private void updateVertexBufferData() {
        FloatBuffer vertexData = createVertexBufferData();

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexData);
    }

    private FloatBuffer createVertexBufferData() {
        FloatBuffer vertexData = BufferUtils.createFloatBuffer(21);

        float x = position.getX();
        float y = position.getY();

        float[] vertexArray = new float[] {
                x, y + 4.0f, 0,
                x + 48.0f, y + 4.0f, 0,
                x + 48.0f, y + 20.0f, 0,
                x, y + 20.0f, 0,
                x + 48.0f, y - 4.0f, 0,
                x + 48.0f, y + 28.0f, 0,
                x + 64.0f, y + 12.0f, 0
        };

        vertexData.put(vertexArray).flip();
        return vertexData;
    }

    private void prepare() {
        attach();
        uploadUniforms();
        enableVertexAttribArrays();
    }

    private void attach() {
        glUseProgram(getProgramId());
        glBindVertexArray(vaoId);
    }

    private void uploadUniforms() {
        uploadUniformMatrix("uProjection", cameraPosition.getProjection());
        uploadUniformMatrix("uView", cameraPosition.getView());
    }

    private void enableVertexAttribArrays() {
        glEnableVertexAttribArray(0);
    }

    private void draw() {
        glDrawElements(GL_TRIANGLES, 9, GL_UNSIGNED_INT, 0);
    }

    private void cleanup() {
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glUseProgram(0);
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
