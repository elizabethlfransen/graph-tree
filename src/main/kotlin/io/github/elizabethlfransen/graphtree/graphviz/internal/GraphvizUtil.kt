package io.github.elizabethlfransen.graphtree.graphviz.internal

import guru.nidi.graphviz.attribute.Attributed
import guru.nidi.graphviz.attribute.Attributes.attr
import guru.nidi.graphviz.attribute.For

internal fun <F : For>  label(label: String) = attr<F>("label", label)
internal fun <T, F : For> Attributed<T, F>.label(label: String) = with(label<F>(label))
