package cf.brforgers.core.lib;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

/**
 * Helper requested by Rennan.
 * @author TheFreeHigh
 *
 */
@SuppressWarnings("unchecked")
public class EntityRegister
{
	/**
	 * Register a Entity.
	 * @param entity Entity Class (Must Extends a Entity)
	 * @param name a Unique Name for the Entity
	 * @param mod The @Mod.Instance
	 * @param trackingRange The range at which MC will send tracking updates
	 * @param updateFrequency The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 * @return EntityID of the Entity
	 */
	public static int register(Class <? extends Entity> entity, String name, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entity, name, entityID);
		EntityRegistry.registerModEntity(entity, name, entityID, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
		
		return entityID;
	}
	
	/**
	 * Registers a Entity Spawn Egg.
	 * @param entityID EntityID (from {@link EntityRegister}.register())
	 * @param mainColor The Main color of the Egg
	 * @param subColor The Sub color of the Egg
	 */
	public static void registerSpawnEgg(int entityID, int mainColor, int subColor)
	{
		EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, mainColor, subColor));
	}
	
	/**
	 * Register a Entity and it's Spawn Egg.
	 * @param entity Entity Class (Must Extends a Entity)
	 * @param name a Unique Name for the Entity
	 * @param mod The MOD Object
	 * @param trackingRange The range at which MC will send tracking updates
	 * @param updateFrequency The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 * @param mainColor The Main color of the Egg
	 * @param subColor The Sub color of the Egg
	 * @return EntityID of the Entity
	 */
	public static int register(Class <? extends Entity> entity, String name, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int mainColor, int subColor)
	{
		int id = register(entity, name, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
		registerSpawnEgg(id, mainColor, subColor);
		return id;
	}
}
