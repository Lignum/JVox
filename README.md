# JVox

## Example
```Java
InputStream stream = ...;
VoxFile voxFile = null;

try (VoxReader reader = new VoxReader(stream)) {
    // VoxReader::read will never return null,
    // but it can throw an InvalidVoxException.
    voxFile = reader.read();
} catch (IOException e) {
    e.printStackTrace();
}

// VoxFile::getMaterials returns a map from material ID to material.
// If your vox file contains the deprecated materials
// stored in MATT chunks, use VoxFile::getOldMaterials instead.
HashMap<Integer, VoxMaterial> materials = voxFile.getMaterials();

// VoxFile::getPalette returns the palette used for the model.
// The palette is an array of ints formatted as R8G8B8A8.
int[] palette = voxFile.getPalette();

// VoxFile::getModels returns all the models used in the file.
// Any valid .vox file must contain at least one model,
// therefore models[0] will never be null.
VoxModel[] models = voxFile.getModels();
VoxModel model = models[0];

// And finally, actually retrieving the voxels.
Vector3<Integer> size = model.getSize();
Voxel[] voxels = model.getVoxels();

// These voxels can then be used as such.
Voxel vox = voxels[0];
Vector3<Byte> position = vox.getPosition();
int colour = palette[vox.getColourIndex()];
