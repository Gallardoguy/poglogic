package net.Slash.poglogic.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Jkff extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING2 = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    
    public Jkff(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING2, Direction.NORTH).setValue(POWERED, Boolean.FALSE));
    }
    
    
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING2, pContext.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING2, pRotation.rotate(pState.getValue(FACING2)));
    }
    
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING2)));
    }
    
    
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }
    
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        if (!level.isClientSide) {
            boolean output = jk(level, pos, state);
            if (output != state.getValue(POWERED) && (lastclk != clk) && clk) {
                level.setBlock(pos, state.setValue(POWERED, jk(level, pos, state)), 3);
            }
        }
    }
    
    private boolean F = false;
    boolean clk = false;
    boolean lastclk = false;
    
    private boolean jk(Level level, BlockPos pos, BlockState state) {
        boolean clock = getClock(level, pos, state);
        boolean inputJ = getInputJ(level, pos, state);
        boolean inputK = getInputK(level, pos, state);
        
        if (clock) {
            if (inputJ && inputK) {
                return !F;
            } else if (!inputJ && inputK) {
                return false;
            } else if (inputJ && !inputK) {
                return true;
            }
        }
        F = state.getValue(POWERED);
        return F;
    }
    
    
    private boolean getClock(Level level, BlockPos pos, BlockState state) {
        Direction dir = state.getValue(FACING2).getClockWise(); //idk if directions dont work look here
        BlockPos asd = pos.relative(dir);
        lastclk = clk;
        clk = level.getSignal(asd, dir) > 0;
        return clk;
    }
    
    
    private boolean getInputJ(Level level, BlockPos pos, BlockState state) {
        Direction dir = state.getValue(FACING2);
        BlockPos asd = pos.relative(dir);
        
        return level.getSignal(asd, dir) > 0;
    }
    
    private boolean getInputK(Level level, BlockPos pos, BlockState state) {
        Direction dir = state.getValue(FACING2).getCounterClockWise();
        BlockPos asd = pos.relative(dir);
        
        return level.getSignal(asd, dir) > 0;
    }
    
    /*private boolean getClock(Level level, BlockPos pos, BlockState state) {
        switch (state.getValue(FACING2)) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return level.getSignal(pos.relative(Direction.WEST), Direction.WEST) > 0;
        }
        return false;
    }
    
    private boolean getInputJ(Level level, BlockPos pos, BlockState state) {
        switch (state.getValue(FACING2)) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return level.getSignal(pos.relative(Direction.SOUTH), Direction.SOUTH) > 0;
        }
        return false;
    }
    
    private boolean getInputK(Level level, BlockPos pos, BlockState state) {
        switch (state.getValue(FACING2)) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return level.getSignal(pos.relative(Direction.EAST), Direction.EAST) > 0;
        }
        return false;
    }*/
    
    
    public int getSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) && state.getValue(FACING2) == direction ? 15 : 0;
    }
    
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        for(Direction direction : Direction.values()) {
            level.updateNeighborsAt(pos.relative(direction), this);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
    
    private void updateNeighbours(BlockState p_54681_, Level p_54682_, BlockPos p_54683_) {
        p_54682_.updateNeighborsAt(p_54683_, this);
    }
    
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.add(FACING2, POWERED);
    }
}
