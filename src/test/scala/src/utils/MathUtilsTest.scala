package src.utils

import org.scalatest.funsuite.AnyFunSuite
import src.utils.MathUtils.*

class MathUtilsTest extends AnyFunSuite :

    test("-1 %% 3 == 2") {
        assertResult(2)(-1 %% 3)
    }

    test("0 %% 3 == 0") {
        assertResult(0)(0 %% 3)
    }

    test("1 %% 3 == 1") {
        assertResult(1)(1 %% 3)
    }

    test("2 %% 3 == 2") {
        assertResult(2)(2 %% 3)
    }

    test("3 %% 3 == 0") {
        assertResult(0)(3 %% 3)
    }

    test("4 %% 3 == 1") {
        assertResult(1)(4 %% 3)
    }

    test("-1 >< (0, 2) == 0") {
        assertResult(0)(-1 >< (0, 2))
    }

    test("0 >< (0, 2) == 0") {
        assertResult(0)(0 >< (0, 2))
    }

    test("1 >< (0, 2) == 0") {
        assertResult(1)(1 >< (0, 2))
    }

    test("2 >< (0, 2) == 0") {
        assertResult(2)(2 >< (0, 2))
    }

    test("3 >< (0, 2) == 0") {
        assertResult(2)(3 >< (0, 2))
    }
