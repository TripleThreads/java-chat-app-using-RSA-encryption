package sample.shared;

import java.io.Serializable;
import java.math.BigInteger;

public class PublicKeys implements Serializable {

    private BigInteger publicModulo;

    private BigInteger publicExponent;

    BigInteger getPublicExponent() {
        return publicExponent;
    }

    BigInteger getPublicModulo() {
        return publicModulo;
    }

    void setPublicExponent(BigInteger publicExponent) {
        this.publicExponent = publicExponent;
    }

    void setPublicModulo(BigInteger publicModulo) {
        this.publicModulo = publicModulo;
    }

    @Override
    public String toString() {
        System.out.println("public exp " + this.getPublicExponent());
        System.out.println("public mod " + this.getPublicModulo());
        return super.toString();
    }
}
