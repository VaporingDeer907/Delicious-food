# 美味食物 (Yummy Food) — MC 1.20.1 Forge 移植版

将 1.19.2 模组《美味食物》(BetaV5.0.1) 移植到 **Minecraft 1.20.1 / Forge 47.4.22**。

## 环境要求

- **JDK 17**（
- Gradle 8.1.1
- IDE：IntelliJ IDEA

## 常用命令（项目根目录）

```
gradlew build          # 编译 + 打包（产物在 build/libs/yummy_food-1.0.0.jar）
gradlew runClient      # 启动游戏测试
gradlew runServer      # 启动服务器测试
gradlew runData        # 运行数据生成器
```

## 蒸炉工作逻辑（v1.1.0）

- **4 格布局**：水（水桶）｜燃料（煤炭/木炭）｜食材（仅模组物品）｜成品
- **燃料**：**1 块煤炭或木炭 + 1 桶水** = **120 秒**持续燃烧；点燃时两者同时消耗（水桶留下空桶），燃尽后需再补 1 煤 + 1 水
- **烹饪**：每件 **20 秒**，支持 4 组蒸制配方：
  - 没蒸过的猪肉饺 → 猪肉饺
  - 没蒸过的萝卜饺 → 白萝卜馅饺子
  - 没炒过的猪肉 → 炒猪肉
  - 豌豆粒 → 烤豌豆粒
- **限制**：食材槽只接受上述模组物品，水槽只接受水桶，燃料槽只接受煤
- **反馈**：燃烧时方块 `lit=true`（顶部贴图变化）+ 蒸汽粒子；GUI 显示火焰与进度条（容器数据槽实时同步）
- 破块掉落全部内容物；支持漏斗自动输入/输出（成品格可被漏斗抽出）

## 从 1.19.2 移植的改动清单

| 改动 | 说明 |
|------|------|
| `YummyFoodModTabs` | 重写：`new CreativeModeTab(...)` → `DeferredRegister` + `CreativeModeTab.builder()`（1.20.1 标签页注册方式），所有物品经 `displayItems` 加入 |
| 27 个物品类 | 移除 `Properties.tab(...)`（1.20.1 已删除） |
| 方块类 | `Properties.of(Material.X)` → `Properties.of().mapColor(MapColor.X)`（`Material` 类已删除） |
| `getDrops` 覆写 | 参数 `LootContext.Builder` → `LootParams.Builder` |
| `BonemealableBlock` | `isValidBonemealTarget` 参数 `BlockGetter` → `LevelReader` |
| `ZhengScreen` | `PoseStack` → `GuiGraphics`（1.20.1 屏幕渲染 API 变更） |
| `ZhengMenu` | `player.level` → `player.level()`（`Entity.level` 变私有） |
| 世界生成 | 代码注册 → **数据包方式**：`data/yummy_food/worldgen/{configured,placed}_feature/*.json`（参数与原代码一致：ga/绿豆/萝卜 tries=32 count=3，豌豆 tries=64 count=10，rarity=32） |
| 贴图目录 | `textures/items`、`textures/blocks`（复数）→ **`textures/item`、`textures/block`（单数）**。1.20.1 贴图集只扫描单数目录，复数目录会导致全部贴图"缺失"（模型引用同步改为 `item/`、`block/` 前缀） |
| `mods.toml` | `loaderVersion [47,)`，补充 `forge` 依赖 |
| `pack.mcmeta` | `pack_format` 9 → 15 |

