# Super Solar Panels - Unofficial

Unofficial maintenance fork of **Denfop's Super Solar Panels** for Minecraft 1.12.2.
The fork is based on [ZelGimi/industrialupgrade](https://github.com/ZelGimi/industrialupgrade)
at commit `ef69c48a` and continues the mod as an independent line under GPL-3.0.

The mod extends IndustrialCraft 2 with four top-tier solar panels and their
supporting equipment, on top of Chocohead's Advanced Solar Panels.

## Features

- Four solar panels: Spectral, Singular, Admin and Photonic.
  Generation, storage, tier and maximum output are all configurable per panel.
- Spectral and Singular Solar Helmets: charge your armor from sunlight, provide
  night vision, breathing and feeding support, and remove negative potion effects
  at an EU cost.
- Enhanced Quantum Helmet, Chestplate, Leggings and Boots.
- Quantum Saber and Spectral Saber: electric weapons that drain nano and quantum
  armor on hit.
- 120k and 240k reactor heat storage cells.
- A wide range of crafting materials: solar cores, wiring, compressed carbon
  materials, and the Sun, Night and Energy runes.
- All SSP and ASP content is grouped in one SuperSolarPanels creative tab.
- Ships with [MoreElectricTools](https://github.com/lr8soft/MoreElectricTools) (METS)
  as a bundled dependency for upcoming linkage content.

## Requirements

| Mod | Version | Notes |
|---|---|---|
| Minecraft | 1.12.2 | Forge 14.23.5.2847 or newer |
| IndustrialCraft 2 | 2.8.x | compiled against 2.8.222-ex112 |
| Advanced Solar Panels (Chocohead) | 4.3.0 | hard dependency (`required-after`) |
| [AdvSolarPatch-Unofficial](https://github.com/mustardwheat/AdvSolarPatch-Unofficial) | 1.2.3 | hard dependency (`required-after`) |

### Why AdvSolarPatch is required

IC2 2.8.191 changed the `InvSlot` family constructors. Advanced Solar Panels 4.3.0
was compiled against IC2 2.8.190 or older and crashes with `NoSuchMethodError` on
newer builds. Our [fork of AdvSolarPatch](https://github.com/mustardwheat/AdvSolarPatch-Unofficial)
fixes this at runtime and additionally makes the `MaxOutput` of the four ASP solar
panels configurable (`AdvancedSPMaxOutput`/`HybrydSPMaxOutput`/`UltimateHSPMaxOutput`/`QuantumSPMaxOutput`
in the ASP config). Install it together with Advanced Solar Panels. The dev runtime
installs it automatically; production instances must add it to `mods/` manually.

## Building

The build is driven by RetroFuturaGradle on Gradle 8.12. Use the included wrapper;
no system Gradle installation is required.

Prerequisites:

- A JDK that can run Gradle 8.12 (Java 17 or newer) available on `JAVA_HOME`.
  The compilation toolchain targets Java 8 and is provisioned automatically.
- The first run downloads the Minecraft and Forge artifacts only. Every mod
  dependency is vendored under `libs/` and deobfuscated to the active mappings
  at build time, so no remote mod maven is needed.

Common tasks:

| Task | Result |
|---|---|
| `gradlew build` | compiles and packages `build/libs/super_solar_panels-<version>.jar` |
| `gradlew runClient` | starts a dev client with IC2, ASP, AdvSolarPatch, METS and JEI |
| `gradlew runServer` | starts a dev server with IC2, ASP, AdvSolarPatch and METS |

AdvSolarPatch is loaded as a classpath dependency in the dev runtime (it must not be
copied into `run/mods`, or FML discovers it twice). JEI is present in the dev
runtime only and is never required by the shipped jar.

## Continuous integration

- `build.yml` compiles the project and uploads the jar on every push and pull request.
- `release.yml` builds and publishes a GitHub Release whenever a `v*` tag is pushed.

## Configuration

All options live in `config/super_solar_panels.cfg` and apply to both the client
and the server. Keep the values identical on both sides.

- `settings spectral solar panel` / `settings singular solar panel` /
  `settings admin solar panel` / `settings photonic solar panel`: generation,
  storage, tier and `MaxOutput` of each solar panel, one group per panel.
- `settings quantum saber` / `settings spectral saber`: damage, capacity,
  transfer limit and tier of each saber.
- `settings twelve heat storage` / `settings max heat storage`: heat capacity of
  each reactor heat storage cell.
- `settings quantum helmet`: capacity, transfer limit and tier of the Superior
  Quantum Helmet.

## License & credits

GPL-3.0, following the upstream repository.

- Original Super Solar Panels mod by **Denfop**.
- Upstream history by **[ZelGimi](https://github.com/ZelGimi/industrialupgrade)**.
- Advanced Solar Panels by Icedfire, SeNtiMeL and **Chocohead**.
- IC2 compatibility patch by **Su5eD**, maintained as [our fork](https://github.com/mustardwheat/AdvSolarPatch-Unofficial)
  with configurable ASP solar output.
- MoreElectricTools by **[lr8soft](https://github.com/lr8soft/MoreElectricTools)**.
