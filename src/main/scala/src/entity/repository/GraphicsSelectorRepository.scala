package src.entity.repository

import src.entity.selector.GraphicsSelector

trait GraphicsSelectorRepository:
    def findByName(name: String): Option[GraphicsSelector]
