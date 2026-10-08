# Super Solar Panels - Unofficial (v1.2 maintenance fork)

Unofficial maintenance fork of **Denfop's Super Solar Panels v1.2** for Minecraft 1.12.2,
branched from [ZelGimi/industrialupgrade](https://github.com/ZelGimi/industrialupgrade) at
commit `ef69c48a` (2020-04-17, the last commit of the early standalone-SSP phase, matching
the released `[超级太阳能]SuperSolarPanels-1.2.jar` built 2020-05-02).

IndustrialUpgrade (IU) is the direct continuation of this mod; this fork instead preserves
the small, ASP-based v1.2 feature set as a maintenance baseline.

## Source provenance

The "sources" uploaded at `ef69c48a` were themselves an incomplete Procyon decompile
(18 of 21 classes, no resources, no build files, flattened packages). This tree was
reconstructed from the original v1.2 release binary:

1. The release jar was deobfuscated (SRG -> current RFG mappings) with the same
   RetroFuturaGradle pipeline used for the IC2/ASP dependencies.
2. The deobfuscated jar was decompiled with Vineflower.
3. The result was verified against the original binary: identical class count (27)
   and archive entry count (145), and the rebuilt jar reobfuscates to SRG names as expected.

Intentional fixes relative to the original v1.2 binary:

- `@Mod` version `1.0.0` -> `1.2.0` (Denfop never bumped it)
- `mcmod.info` modid `Super_Solar_Panels` -> `super_solar_panels` (case mismatch meant
  the metadata never associated with the mod)

## Dependencies

| Mod | Version | Notes |
|---|---|---|
| Minecraft | 1.12.2 | Forge via RFG |
| IndustrialCraft 2 | compiled against 2.8.222-ex112 | any IC2 the runtime stack supports |
| Advanced Solar Panels (Chocohead) | 4.3.0 | **hard dependency** (`required-after`) |
| AdvSolarPatch (Su5eD) | 1.1 | **required at runtime when IC2 >= 2.8.191** |

### IC2 version caveat

IC2 2.8.191 changed the `InvSlot` / `InvSlotOutput` / `InvSlotProcessable` constructors
from `TileEntityInventory`-first to `IInventorySlotHolder`-first. ASP 4.3.0 was compiled
against IC2 <= 2.8.190 and crashes with `NoSuchMethodError` on newer builds.
[AdvSolarPatch](https://github.com/Su5eD/AdvSolarPatch) fixes this at runtime; this mod's
own code never touches those constructors. `runClient`/`runServer` automatically copy
the vendored `libs/advsolarpatch-1.1.jar` into `run/mods`. On real instances, drop the
same jar into `mods/` when using IC2 2.8.191 or newer (e.g. 2.8.222).

## Building

- Gradle 8.12 (wrapper included) + RetroFuturaGradle 1.4.2, Azul JDK 8 toolchain
- `gradlew build` -> `build/libs/super_solar_panels-1.2.0.jar`
- `gradlew runClient` for a dev client with IC2 + ASP + AdvSolarPatch pre-wired

IC2 and ASP are vendored under `libs/` and deobfuscated by RFG at build time; no remote
mod maven is required to build.

## License & credits

GPL-3.0 (following the upstream repository). Original mod by **Denfop**; Advanced Solar
Panels by Icedfire / SeNtiMeL / **Chocohead**; IC2-compat patch by **Su5eD**; upstream
history by **ZelGimi**; see also the independent later line
[RuiXuqi/Super-Solar-Panels-Remastered](https://github.com/RuiXuqi/Super-Solar-Panels-Remastered) (v1.4.0, MPL-2.0).
