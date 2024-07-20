package io.github.andichrist.math;

public record Complex(double real, double imaginary) {

    public static final Complex ZERO = new Complex(0.0, 0.0);
    public static final Complex ONE = new Complex(1.0, 0.0);

    public double abs() {
        if (Math.abs(real) < Math.abs(imaginary)) {
            if (imaginary == 0.0) {
                return Math.abs(real);
            }
            double q = real / imaginary;
            return Math.abs(imaginary) * Math.sqrt(1 + q * q);
        } else {
            if (real == 0.0) {
                return Math.abs(imaginary);
            }
            double q = imaginary / real;
            return Math.abs(real) * Math.sqrt(1 + q * q);
        }
    }

    public Complex add(Complex c) {
        return new Complex(
                real + c.real(),
                imaginary + c.imaginary()
        );
    }

    public Complex multiply(Complex z) {
        return new Complex(
                real * z.real - imaginary * z.imaginary,
                real * z.imaginary + imaginary * z.real
        );
    }
}
