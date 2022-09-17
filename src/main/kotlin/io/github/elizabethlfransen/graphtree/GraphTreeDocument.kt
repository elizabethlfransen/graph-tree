package io.github.elizabethlfransen.graphtree

import org.antlr.v4.runtime.CharStreams
import java.io.InputStream
import java.io.Reader
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.nio.charset.CodingErrorAction
import java.nio.file.Path

/**
 * Represents root node of a GraphTree document which may contain 0 or more GraphTreeNodes.
 *
 * Each direct child of the document are considered to be their own tree.
 */
data class GraphTreeDocument(
    /**
     * Trees contained within document
     */
    val children: List<GraphTreeNode>
) {
    constructor(vararg nodes: GraphTreeNode) : this(nodes.toList())

    @Suppress("unused")
    companion object {
        fun fromPath(path: Path) = GraphTreeDocumentReader(CharStreams.fromPath(path)).read()
        fun fromPath(path: Path, charset: Charset) = GraphTreeDocumentReader(CharStreams.fromPath(path, charset)).read()
        fun fromFileName(fileName: String) = GraphTreeDocumentReader(CharStreams.fromFileName(fileName)).read()
        fun fromFileName(fileName: String, charset: Charset) = GraphTreeDocumentReader(CharStreams.fromFileName(fileName, charset)).read()
        fun fromStream(`is`: InputStream) = GraphTreeDocumentReader(CharStreams.fromStream(`is`)).read()
        fun fromStream(`is`: InputStream, charset: Charset) = GraphTreeDocumentReader(CharStreams.fromStream(`is`, charset)).read()
        fun fromStream(`is`: InputStream, charset: Charset, inputSize: Long) = GraphTreeDocumentReader(CharStreams.fromStream(`is`, charset, inputSize)).read()
        fun fromChannel(channel: ReadableByteChannel) = GraphTreeDocumentReader(CharStreams.fromChannel(channel)).read()
        fun fromChannel(channel: ReadableByteChannel, charset: Charset) = GraphTreeDocumentReader(CharStreams.fromChannel(channel, charset)).read()
        fun fromReader(r: Reader) = GraphTreeDocumentReader(CharStreams.fromReader(r)).read()
        fun fromReader(r: Reader, sourceName: String) = GraphTreeDocumentReader(CharStreams.fromReader(r, sourceName)).read()
        fun fromString(s: String) = GraphTreeDocumentReader(CharStreams.fromString(s)).read()
        fun fromString(s: String, sourceName: String) = GraphTreeDocumentReader(CharStreams.fromString(s, sourceName)).read()
        fun fromChannel(channel: ReadableByteChannel, bufferSize: Int, decodingErrorAction: CodingErrorAction, sourceName: String) = GraphTreeDocumentReader(CharStreams.fromChannel(channel, bufferSize, decodingErrorAction, sourceName)).read()
        fun fromChannel(channel: ReadableByteChannel, charset: Charset, bufferSize: Int, decodingErrorAction: CodingErrorAction, sourceName: String, inputSize: Long) = GraphTreeDocumentReader(CharStreams.fromChannel(channel, charset, bufferSize, decodingErrorAction, sourceName, inputSize)).read()
    }
}