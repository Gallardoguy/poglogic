package net.Slash.poglogic.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AndGate extends HorizontalDirectionalBlock {
    
    public static final DirectionProperty FACING2 = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    
    public AndGate(Properties properties) {
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
            if (And(level, pos, state)) {
                level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE), 3);
            } else {
                level.setBlock(pos, state.setValue(POWERED, Boolean.FALSE), 3);
            }
        }
    }
    
    private boolean And(Level level, BlockPos pos, BlockState state){
        return hasOneOn(level, pos, state) && hasTwoOn(level, pos, state);
    }
    
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {
        if (state.getValue(POWERED) && And(level, pos, state)) {
            level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE), 2);
        }
    }
    
    private boolean hasTwoOn(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(FACING2) == Direction.NORTH || state.getValue(FACING2) == Direction.SOUTH) {
            return level.getSignal(pos.relative(Direction.EAST), Direction.EAST) > 0;
        } else if (state.getValue(FACING2) == Direction.EAST || state.getValue(FACING2) == Direction.WEST) {
            return level.getSignal(pos.relative(Direction.NORTH), Direction.NORTH) > 0;
        }
        return false;
    }
    
    private boolean hasOneOn(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(FACING2) == Direction.NORTH || state.getValue(FACING2) == Direction.SOUTH) {
            return level.getSignal(pos.relative(Direction.WEST), Direction.WEST) > 0;
        } else if (state.getValue(FACING2) == Direction.EAST || state.getValue(FACING2) == Direction.WEST) {
            return level.getSignal(pos.relative(Direction.SOUTH), Direction.SOUTH) > 0;
        }
        return false;
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
        for(Direction direction : Direction.values()) {
            level.updateNeighborsAt(pos.relative(direction), this);
        }
    }
    
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.add(FACING2, POWERED);
    }
}
