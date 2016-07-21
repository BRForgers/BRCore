package cf.brforgers.core.lib.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WorldBlockPos extends BlockPos {
    private final World world;

    public WorldBlockPos(World world, int x, int y, int z) {
        super(x, y, z);
        this.world = world;
    }

    public WorldBlockPos(World world, double x, double y, double z) {
        this(world, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public WorldBlockPos(World world, Entity source) {
        this(world, source.posX, source.posY, source.posZ);
    }

    public WorldBlockPos(World world, Vec3d vec) {
        this(world, vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public WorldBlockPos(World world, Vec3i source) {
        this(world, source.getX(), source.getY(), source.getZ());
    }

    public World getWorld() {
        return world;
    }

    public IBlockState getBlockState() {
        return world.getBlockState(this);
    }

    public boolean setBlockState(IBlockState state) {
        return world.setBlockState(this, state);
    }

    public boolean setBlockToAir() {
        return world.setBlockToAir(this);
    }
}

