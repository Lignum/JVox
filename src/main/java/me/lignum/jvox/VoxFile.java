package me.lignum.jvox;

public final class VoxFile {
    private final int version;
    private final VoxRootChunk root;

    protected VoxFile(int version, VoxRootChunk root) {
        this.version = version;
        this.root = root;
    }

    public VoxModel[] getModels() {
        return root.getModels();
    }

    public int[] getPalette() {
        return root.getPalette();
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "VoxFile{version=" + version + "}";
    }
}
