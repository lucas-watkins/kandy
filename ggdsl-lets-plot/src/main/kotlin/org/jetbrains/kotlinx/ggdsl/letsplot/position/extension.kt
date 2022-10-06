/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.position

import org.jetbrains.kotlinx.ggdsl.dsl.contexts.LayerContext

/**
 * Position adjustment of this layer.
 *
 * @see [Position]
 */
public var LayerContext.position: Position
    get() = Position.Identity // todo add backing property?
    set(pos) {
        features[Position.FEATURE_NAME] = pos
    }
