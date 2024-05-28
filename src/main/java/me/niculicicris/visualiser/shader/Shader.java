package me.niculicicris.visualiser.shader;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {
    private int vertexId;
    private int fragmentId;
    private int programId;

    public Shader(String vertexSource, String fragmentSource) {
        loadVertexShader(vertexSource);
        loadFragmentShader(fragmentSource);
        createProgram();
        createObjects();
    }

    private void loadVertexShader(String vertexSource) {
        String source = readFile(vertexSource);

        assembleVertexShader(source);
        checkVertexShaderErrors();
    }

    private void assembleVertexShader(String source) {
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, source);
        glCompileShader(vertexId);
    }

    private void checkVertexShaderErrors() {
        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            String error = glGetShaderInfoLog(vertexId);
            throw new RuntimeException(error);
        }
    }

    private void loadFragmentShader(String fragmentSource) {
        String source = readFile(fragmentSource);

        assembleFragmentShader(source);
        checkFragmentShaderErrors();
    }

    private void assembleFragmentShader(String source) {
        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, source);
        glCompileShader(fragmentId);
    }

    private void checkFragmentShaderErrors() {
        int success = glGetShaderi(fragmentId, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            String error = glGetShaderInfoLog(fragmentId);
            throw new RuntimeException(error);
        }
    }

    private void createProgram() {
        linkShaders();
        checkProgramErrors();
    }

    private void linkShaders() {
        programId = glCreateProgram();

        glAttachShader(programId, vertexId);
        glAttachShader(programId, fragmentId);
        glLinkProgram(programId);
    }

    private void checkProgramErrors() {
        int success = glGetProgrami(programId, GL_LINK_STATUS);

        if (success == GL_FALSE) {
            String error = glGetProgramInfoLog(programId);
            throw new RuntimeException(error);
        }
    }

    private String readFile(String path) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream(path);

        if (is == null) return null;
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException exception) {
            return null;
        }
    }

    private void createObjects() {
        createVertexArrayObject();
        createVertexBufferObject();
        createElementBufferObject();
        createAttributePointers();
    }

    protected abstract void createVertexArrayObject();

    protected abstract void createVertexBufferObject();

    protected abstract void createElementBufferObject();

    protected abstract void createAttributePointers();

    public abstract void use();

    protected int getProgramId() {
        return programId;
    }

    protected void uploadUniformMatrix(String name, Matrix4f matrix) {
        int uniformLocation = glGetUniformLocation(programId, name);

        if (uniformLocation == -1) {
            throw new RuntimeException("Could not find uniform " + name + ".");
        }
        FloatBuffer matrixBuffer = createMatrixBuffer(matrix);
        glUniformMatrix4fv(uniformLocation, false, matrixBuffer);
    }

    private FloatBuffer createMatrixBuffer(Matrix4f matrix) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        return matrix.get(matrixBuffer);
    }
}
