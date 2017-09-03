package com.arny.celestiatools.utils.astro;

public class Dimensions {

    public int width;
    public int height;

    public Dimensions() {}

    public Dimensions(int w, int h) {
        width = w;
        height = h;
    }

    public Dimensions(Dimensions p) {
        this.width = p.width;
        this.height = p.height;
    }

    public final void set(int w, int h) {
        width = w;
        height = h;
    }

    public final void set(Dimensions d) {
        this.width = d.width;
        this.height = d.height;
    }

    public final boolean equals(int w, int h) {
        return this.width == w && this.height == h;
    }

    public final boolean equals(Object o) {
        return o instanceof Dimensions && (o == this || equals(((Dimensions)o).width, ((Dimensions)o).height));
    }

}