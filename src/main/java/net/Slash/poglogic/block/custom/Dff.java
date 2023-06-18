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

public class Dff extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING2 = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private boolean D = false;
    public Dff(Properties properties) {
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
    
    boolean clk = false;
    boolean lastclk = false;
    
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        if (!level.isClientSide) {
            boolean output = dff(level, pos, state);
            
            if (output != state.getValue(POWERED) && (lastclk != clk) && clk) {
                level.setBlock(pos, state.setValue(POWERED, output), 3);
            }
        }
    }
    
    
    
    private boolean dff(Level level, BlockPos pos, BlockState state) {
        boolean clock = getClock(level, pos, state);
        boolean input = getInput(level, pos, state);
        if (clock) {
            D = input;
            return D;
        }
        return D;
    }
    
    
    private boolean getClock(Level level, BlockPos pos, BlockState state) {
        Direction dir = state.getValue(FACING2).getClockWise(); //idk if directions dont work look here
        BlockPos asd = pos.relative(dir);
        lastclk = clk;
        clk = level.getSignal(asd, dir) > 0;
        return clk;
    }
    
    
    private boolean getInput(Level level, BlockPos pos, BlockState state) {
        Direction dir = state.getValue(FACING2);
        BlockPos asd = pos.relative(dir);
    
        return level.getSignal(asd, dir) > 0;
    }
    
    
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
    
    @Override
    public void onPlace(BlockState state1, Level level, BlockPos pos, BlockState state, boolean b) {
        //level.setBlock(pos, state1.setValue(POWERED, false), 3);
        for(Direction direction : Direction.values()) {
            level.updateNeighborsAt(pos.relative(direction), this);
        }
    }
    
    
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.add(FACING2, POWERED);
    }
}
