package src.data.repository

import src.game.entity.selector.GraphicsSelector

@Deprecated
trait GraphicsSelectorRepository:
    def findByName(name: String): Option[GraphicsSelector]
