
package chaosgame.domain;

/**
 * The different types of Node in a fractal.
 * - Empty: Has not been drawn over or used in any way
 * - Filled: The fractal drawing iteration has passed over this point
 * - Anchor: The Nodes used to generate the fractal, these are chosen at random in the iterating process.
 */
public enum Nodetype {
    EMPTY, FILLED, ANCHOR;
}
