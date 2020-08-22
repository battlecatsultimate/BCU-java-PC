package utilpcimport

import common.pack.PackData
import common.util.Data
import common.util.lang.Formatter
import page.Page
import java.util.*

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
object Interpret : Data {
    /** enemy types  */
    var ERARE: Array<String>

    /** unit rarities  */
    var RARITY: Array<String>

    /** enemy traits  */
    var TRAIT: Array<String>

    /** star names  */
    var STAR: Array<String>

    /** ability name  */
    var ABIS: Array<String>

    /** enemy ability name  */
    var EABI: Array<String?>
    var SABIS: Array<String>
    var PROC: Array<String>
    var SPROC: Array<String>
    var TREA: Array<String>
    var TEXT: Array<String>
    var ATKCONF: Array<String>
    var COMF: Array<String>
    var COMN: Array<String>
    var TCTX: Array<String>
    var PCTX: Array<String>

    /** treasure orderer  */
    val TIND = intArrayOf(0, 1, 18, 19, 20, 21, 22, 23, 2, 3, 4, 5, 24, 25, 26, 27, 28, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17, 29, 30, 31, 32, 33, 34, 35, 36)

    /** treasure grouper  */
    val TCOLP = arrayOf(intArrayOf(0, 6), intArrayOf(8, 6), intArrayOf(14, 3), intArrayOf(17, 4), intArrayOf(21, 3), intArrayOf(29, 8))

    /** treasure max  */
    private val TMAX = intArrayOf(30, 30, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 600, 1500, 100,
            100, 100, 30, 30, 30, 30, 30, 10, 300, 300, 600, 600, 600, 20, 30, 30, 30, 20, 20, 20, 20)

    /** proc data formatter  */
    private val CMP = arrayOf(intArrayOf(0, -1), intArrayOf(0, -1, 1), intArrayOf(0, -1, 1), intArrayOf(0, -1), intArrayOf(0, 2, -1), intArrayOf(0, -1, 3, 1), intArrayOf(0, -1), intArrayOf(0, -1, 1, 4), intArrayOf(0, -1, 1), intArrayOf(5, -1, 7), intArrayOf(0, -1), intArrayOf(-1, 4, 6), intArrayOf(-1, 1, 5, 6), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(-1, 7), intArrayOf(0, -1), intArrayOf(0, -1, 1), intArrayOf(0, -1, 1), intArrayOf(0, -1, 4), intArrayOf(0, -1, 1), intArrayOf(0, -1, 1), intArrayOf(0, -1), intArrayOf(0, -1), intArrayOf(-1), intArrayOf(0, -1, 7), intArrayOf(0, -1, 1), intArrayOf(0, -1, 7), intArrayOf(0, -1, 4, 8, 1), intArrayOf(-1), intArrayOf(-1), intArrayOf(0, -1, 1, 3), intArrayOf(0, -1, 1))

    /** combo string component  */
    private val CDP = arrayOf(arrayOf("", "+", "-"), arrayOf("_", "_%", "_f", "Lv._"))

    /** combo string formatter  */
    private val CDC = arrayOf(intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 3), intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(2, 2), intArrayOf(), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(2, 2), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1), intArrayOf(1, 1))
    val EABIIND = intArrayOf(5, 7, 8, 9, 10, 11, 12, 15, 16, 18, 113, 114, 115, 116, 117, 118, 119, 133,
            134)
    val ABIIND = intArrayOf(113, 114, 115, 116, 117, 118, 119, 133, 134)
    const val IMUSFT = 13
    const val EFILTER = 8
    fun allRangeSame(me: MaskEntity): Boolean {
        return if (me is CustomEntity) {
            val near: MutableList<Int> = ArrayList()
            val far: MutableList<Int> = ArrayList()
            for (atk in (me as CustomEntity).atks) {
                near.add(atk.getShortPoint())
                far.add(atk.getLongPoint())
            }
            if (near.isEmpty() && far.isEmpty()) {
                return true
            }
            for (n in near) {
                if (n != near[0]) {
                    return false
                }
            }
            for (f in far) {
                if (f != far[0]) {
                    return false
                }
            }
            true
        } else {
            true
        }
    }

    fun comboInfo(c: Combo): String {
        return combo(c.type, CommonStatic.getBCAssets().values.get(c.type).get(c.lv))
    }

    fun comboInfo(inc: IntArray): Array<String> {
        val ls: MutableList<String> = ArrayList()
        for (i in 0 until Data.Companion.C_TOT) {
            if (inc[i] == 0) continue
            ls.add(combo(i, inc[i]))
        }
        return ls.toTypedArray()
    }

    fun getAbi(me: MaskEntity): List<String> {
        val tb: Int = me.touchBase()
        val ma: MaskAtk
        ma = if (me.getAtkCount() == 1) {
            me.getAtkModel(0)
        } else {
            me.getRepAtk()
        }
        val lds: Int
        val ldr: Int
        if (allRangeSame(me)) {
            lds = me.getAtkModel(0).getShortPoint()
            ldr = me.getAtkModel(0).getLongPoint() - me.getAtkModel(0).getShortPoint()
        } else {
            lds = ma.getShortPoint()
            ldr = ma.getLongPoint() - ma.getShortPoint()
        }
        val l: MutableList<String> = ArrayList()
        if (lds > 0) {
            val p0 = Math.min(lds, lds + ldr)
            val p1 = Math.max(lds, lds + ldr)
            val r = Math.abs(ldr)
            l.add(Page.Companion.get(3, "ld0") + ": " + tb + ", " + Page.Companion.get(3, "ld1") + ": " + p0 + "~" + p1 + ", "
                    + Page.Companion.get(3, "ld2") + ": " + r)
        }
        var imu: String = Page.Companion.get(3, "imu")
        for (i in ABIS.indices) if (me.getAbi() shr i and 1 > 0) if (ABIS[i].startsWith("IMU")) imu += ABIS[i].substring(3) + ", " else l.add(ABIS[i])
        if (imu.length > 10) l.add(imu)
        return l
    }

    fun getComboFilter(n: Int): Array<String?> {
        val res: IntArray = CommonStatic.getBCAssets().filter.get(n)
        val strs = arrayOfNulls<String>(res.size)
        for (i in res.indices) strs[i] = COMN[res[i]]
        return strs
    }

    fun getComp(ind: Int, t: Treasure): Int {
        var ans = -2
        for (i in 0 until TCOLP[ind][1]) {
            val temp = getValue(TIND[i + TCOLP[ind][0]], t)
            if (ans == -2) ans = temp else if (ans != temp) return -1
        }
        return ans
    }

    fun getProc(de: MaskEnemy): List<String> {
        val ctx = Formatter.Context(true, false)
        val l: MutableList<String> = ArrayList()
        val ma: MaskAtk = de.getRepAtk()
        for (i in PROC.indices) {
            val item: ProcItem = ma.getProc().getArr(i)
            if (!item.exists()) continue
            val format: String = ProcLang.Companion.get().get(i).format
            val formatted: String = Formatter.Companion.format(format, item, ctx)
            l.add(formatted)
        }
        return l
    }

    fun getProc(du: MaskEntity, t: Treasure?, trait: Int): List<String> {
        val l: MutableList<String> = ArrayList()
        val ctx = Formatter.Context(false, false)
        val ma: MaskAtk = du.getRepAtk()
        for (i in PROC.indices) {
            val item: ProcItem = ma.getProc().getArr(i)
            if (!item.exists()) continue
            val format: String = ProcLang.Companion.get().get(i).format
            val formatted: String = Formatter.Companion.format(format, item, ctx)
            l.add(formatted)
        }
        return l
    }

    fun getTrait(type: Int, star: Int): String {
        var ans = ""
        for (i in TRAIT.indices) if (type shr i and 1 > 0) ans += TRAIT[i] + ", "
        if (star > 0) ans += STAR[star]
        return ans
    }

    operator fun getValue(ind: Int, t: Treasure): Int {
        if (ind == 0) return t.tech.get(Data.Companion.LV_RES) else if (ind == 1) return t.tech.get(Data.Companion.LV_ACC) else if (ind == 2) return t.trea.get(Data.Companion.T_ATK) else if (ind == 3) return t.trea.get(Data.Companion.T_DEF) else if (ind == 4) return t.trea.get(Data.Companion.T_RES) else if (ind == 5) return t.trea.get(Data.Companion.T_ACC) else if (ind == 6) return t.fruit.get(Data.Companion.T_RED) else if (ind == 7) return t.fruit.get(Data.Companion.T_FLOAT) else if (ind == 8) return t.fruit.get(Data.Companion.T_BLACK) else if (ind == 9) return t.fruit.get(Data.Companion.T_ANGEL) else if (ind == 10) return t.fruit.get(Data.Companion.T_METAL) else if (ind == 11) return t.fruit.get(Data.Companion.T_ZOMBIE) else if (ind == 12) return t.fruit.get(Data.Companion.T_ALIEN) else if (ind == 13) return t.alien else if (ind == 14) return t.star else if (ind == 15) return t.gods.get(0) else if (ind == 16) return t.gods.get(1) else if (ind == 17) return t.gods.get(2) else if (ind == 18) return t.tech.get(Data.Companion.LV_BASE) else if (ind == 19) return t.tech.get(Data.Companion.LV_WORK) else if (ind == 20) return t.tech.get(Data.Companion.LV_WALT) else if (ind == 21) return t.tech.get(Data.Companion.LV_RECH) else if (ind == 22) return t.tech.get(Data.Companion.LV_CATK) else if (ind == 23) return t.tech.get(Data.Companion.LV_CRG) else if (ind == 24) return t.trea.get(Data.Companion.T_WORK) else if (ind == 25) return t.trea.get(Data.Companion.T_WALT) else if (ind == 26) return t.trea.get(Data.Companion.T_RECH) else if (ind == 27) return t.trea.get(Data.Companion.T_CATK) else if (ind == 28) return t.trea.get(Data.Companion.T_BASE) else if (ind == 29) return t.bslv.get(Data.Companion.BASE_H) else if (ind == 30) return t.bslv.get(Data.Companion.BASE_SLOW) else if (ind == 31) return t.bslv.get(Data.Companion.BASE_WALL) else if (ind == 32) return t.bslv.get(Data.Companion.BASE_STOP) else if (ind == 33) return t.bslv.get(Data.Companion.BASE_WATER) else if (ind == 34) return t.bslv.get(Data.Companion.BASE_GROUND) else if (ind == 35) return t.bslv.get(Data.Companion.BASE_BARRIER) else if (ind == 36) return t.bslv.get(Data.Companion.BASE_CURSE)
        return -1
    }

    fun isER(e: Enemy, t: Int): Boolean {
        if (t == 0) return e.inDic
        if (t == 1) return e.de.getStar() == 1
        val lis: List<MapColc> = e.findMap()
        var colab = false
        if (lis.contains(DefMapColc.Companion.getMap("C"))) if (lis.size == 1) colab = true else if (lis.size == 2) colab = lis.contains(DefMapColc.Companion.getMap("R")) || lis.contains(DefMapColc.Companion.getMap("CH"))
        if (t == 2) return !colab
        if (t == 3) return !colab && !e.inDic
        if (t == 4) return colab
        return if (t == 5) e.id.pack != PackData.Identifier.Companion.DEF else false
    }

    fun isType(de: MaskEntity, type: Int): Boolean {
        val raw: Array<IntArray> = de.rawAtkData()
        if (type == 0) return !de.isRange() else if (type == 1) return de.isRange() else if (type == 2) return de.isLD() else if (type == 3) return raw.size > 1 else if (type == 4) return de.isOmni() else if (type == 5) return de.getTBA() + raw[0][1] < de.getItv() / 2
        return false
    }

    fun redefine() {
        ERARE = Page.Companion.get(3, "er", 6)
        RARITY = Page.Companion.get(3, "r", 6)
        TRAIT = Page.Companion.get(3, "c", 12)
        STAR = Page.Companion.get(3, "s", 5)
        ABIS = Page.Companion.get(3, "a", 22)
        SABIS = Page.Companion.get(3, "sa", 22)
        ATKCONF = Page.Companion.get(3, "aa", 6)
        PROC = Page.Companion.get(3, "p", CMP.size)
        SPROC = Page.Companion.get(3, "sp", CMP.size)
        TREA = Page.Companion.get(3, "t", 37)
        TEXT = Page.Companion.get(3, "d", 9)
        COMF = Page.Companion.get(3, "na", 6)
        COMN = Page.Companion.get(3, "nb", 25)
        TCTX = Page.Companion.get(3, "tc", 6)
        PCTX = Page.Companion.get(3, "aq", 52)
        EABI = arrayOfNulls(EABIIND.size)
        for (i in EABI.indices) {
            if (EABIIND[i] < 100) EABI[i] = SABIS[EABIIND[i]] else EABI[i] = SPROC[EABIIND[i] - 100]
        }
    }

    fun setComp(ind: Int, v: Int, b: BasisSet) {
        for (i in 0 until TCOLP[ind][1]) setValue(TIND[i + TCOLP[ind][0]], v, b)
    }

    fun setValue(ind: Int, v: Int, b: BasisSet) {
        setVal(ind, v, b.t())
        for (bl in b.lb) setVal(ind, v, bl.t())
    }

    private fun combo(t: Int, `val`: Int): String {
        val con = CDC[t]
        return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replace("_".toRegex(), "" + `val`)
    }

    private fun setVal(ind: Int, v: Int, t: Treasure) {
        var v = v
        if (v < 0) v = 0
        if (v > TMAX[ind]) v = TMAX[ind]
        if (ind == 0) t.tech.get(Data.Companion.LV_RES) = v else if (ind == 1) t.tech.get(Data.Companion.LV_ACC) = v else if (ind == 2) t.trea.get(Data.Companion.T_ATK) = v else if (ind == 3) t.trea.get(Data.Companion.T_DEF) = v else if (ind == 4) t.trea.get(Data.Companion.T_RES) = v else if (ind == 5) t.trea.get(Data.Companion.T_ACC) = v else if (ind == 6) t.fruit.get(Data.Companion.T_RED) = v else if (ind == 7) t.fruit.get(Data.Companion.T_FLOAT) = v else if (ind == 8) t.fruit.get(Data.Companion.T_BLACK) = v else if (ind == 9) t.fruit.get(Data.Companion.T_ANGEL) = v else if (ind == 10) t.fruit.get(Data.Companion.T_METAL) = v else if (ind == 11) t.fruit.get(Data.Companion.T_ZOMBIE) = v else if (ind == 12) t.fruit.get(Data.Companion.T_ALIEN) = v else if (ind == 13) t.alien = v else if (ind == 14) t.star = v else if (ind == 15) t.gods.get(0) = v else if (ind == 16) t.gods.get(1) = v else if (ind == 17) t.gods.get(2) = v else if (ind == 18) t.tech.get(Data.Companion.LV_BASE) = v else if (ind == 19) t.tech.get(Data.Companion.LV_WORK) = v else if (ind == 20) t.tech.get(Data.Companion.LV_WALT) = v else if (ind == 21) t.tech.get(Data.Companion.LV_RECH) = v else if (ind == 22) t.tech.get(Data.Companion.LV_CATK) = v else if (ind == 23) t.tech.get(Data.Companion.LV_CRG) = v else if (ind == 24) t.trea.get(Data.Companion.T_WORK) = v else if (ind == 25) t.trea.get(Data.Companion.T_WALT) = v else if (ind == 26) t.trea.get(Data.Companion.T_RECH) = v else if (ind == 27) t.trea.get(Data.Companion.T_CATK) = v else if (ind == 28) t.trea.get(Data.Companion.T_BASE) = v else if (ind == 29) t.bslv.get(Data.Companion.BASE_H) = v else if (ind == 30) t.bslv.get(Data.Companion.BASE_SLOW) = v else if (ind == 31) t.bslv.get(Data.Companion.BASE_WALL) = v else if (ind == 32) t.bslv.get(Data.Companion.BASE_STOP) = v else if (ind == 33) t.bslv.get(Data.Companion.BASE_WATER) = v else if (ind == 34) t.bslv.get(Data.Companion.BASE_GROUND) = v else if (ind == 35) t.bslv.get(Data.Companion.BASE_BARRIER) = v else if (ind == 36) t.bslv.get(Data.Companion.BASE_CURSE) = v
    }

    init {
        redefine()
    }
}