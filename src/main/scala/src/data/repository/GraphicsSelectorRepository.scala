package src.data.repository

import src.game.entity.selector.GraphicsSelector

trait GraphicsSelectorRepository:
    def findByName(name: String): Option[GraphicsSelector]
