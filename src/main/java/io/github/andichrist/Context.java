package io.github.andichrist;

public class Context {
  private FractalStrategy strategy;

  public void setStrategy(FractalStrategy strategy) {
    this.strategy = strategy;
  }

  public int[] compute() {
    return strategy.compute();
  }
}
