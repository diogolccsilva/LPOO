package com.lpoo.project.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Vasco on 13/05/2016.
 */
public class Animator {

    private Animation attack, move;
    private TextureAtlas attackTextures, moveTextures;
    private float stateTime;

    /**
     * @brief Constructor for the Animator class
     * @param attackPath
     * @param movePath
     * @param attackSpeed
     * @param moveSpeed
     */
    public Animator( String attackPath, String movePath, float attackSpeed, float moveSpeed ) {

        stateTime = 0;

        attackTextures = new TextureAtlas( Gdx.files.internal( attackPath ) );
        attack = new Animation( attackSpeed, attackTextures.getRegions() );

        moveTextures = new TextureAtlas( Gdx.files.internal( movePath ) );
        move = new Animation( moveSpeed, moveTextures.getRegions() );
    }

    /**
     * @biref Sets the speed of the attack animation
     * @param speed
     */
    public void setAttackSpeed ( float speed ) {

        attack.setFrameDuration( speed );
    }

    /**
     * @biref Sets the speed of the attack animation
     * @param speed
     */
    public void setMoveSpeed ( float speed ) {

        move.setFrameDuration( speed );
    }

    /**
     * @brief Getter for the current texture of the animation
     * @param delta
     * @return TextureRegion to be drawn on the screen
     */
    public TextureRegion getTexture ( float delta ) {

        stateTime += delta;
        return attack.getKeyFrame( stateTime, true );
    }
}
