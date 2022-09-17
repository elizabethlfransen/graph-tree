package io.github.elizabethlfransen.graphtree.app

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter


fun main(args: Array<String>) = GraphTreeCommand()
    .context {
        helpFormatter = CliktHelpFormatter(showDefaultValues = true)
    }
    .main(args)