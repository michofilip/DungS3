package src.data.repository

import src.game.entity.selector.AnimationSelector

trait AnimationSelectorRepository:
    def findByName(name: String): Option[AnimationSelector]
