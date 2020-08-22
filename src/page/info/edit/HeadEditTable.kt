package page.info.editimport

import common.pack.PackData
import common.util.pack.Background
import common.util.stage.Stage
import page.Page
import java.awt.event.ActionEvent
import javax.swing.JLabel

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
internal class HeadEditTable(p: Page, pack: UserPack) : Page(p) {
    private val hea: JL = JL(1, "ht00")
    private val len: JL = JL(1, "ht01")
    private val mus: JBTN = JBTN(1, "mus")
    private val max: JL = JL(1, "ht02")
    private val bg: JBTN = JBTN(1, "ht04")
    private val cas: JBTN = JBTN(1, "ht05")
    private val name: JTF = JTF()
    private val jhea: JTF = JTF()
    private val jlen: JTF = JTF()
    private val jbg: JTF = JTF()
    private val jcas: JTF = JTF()
    private val jm0: JTF = JTF()
    private val jmh: JTF = JTF()
    private val jm1: JTF = JTF()
    private val con: JTG = JTG(1, "ht03")
    private val star: Array<JTF?> = arrayOfNulls<JTF>(4)
    private val jmax: JTF = JTF()
    private val loop: JL = JL(1, "lop")
    private val loop1: JL = JL(1, "lop1")
    private val lop: JTF = JTF()
    private val lop1: JTF = JTF()
    private val lt: LimitTable
    private var sta: Stage? = null
    private val pac: UserPack
    private var bvp: BGViewPage? = null
    private var cvp: CastleViewPage? = null
    private var mp: MusicPage? = null
    private var musl = 0
    override fun callBack(o: Any?) {
        setData(sta)
    }

    public override fun renew() {
        lt.renew()
        if (bvp != null) {
            val `val`: PackData.Identifier<Background> = bvp.getSelected().id ?: return
            jbg.setText(`val`.toString())
            sta!!.bg = `val`
        }
        if (cvp != null) {
            val `val`: PackData.Identifier<CastleImg> = cvp.getVal() ?: return
            jcas.setText(`val`.toString())
            sta!!.castle = `val`
        }
        if (mp != null) {
            val jtf: JTF = if (musl == 0) jm0 else jm1
            val `val`: PackData.Identifier<Music> = mp.getSelected()
            jtf.setText("" + `val`)
            if (jtf === jm0) {
                sta!!.mus0 = `val`
                if (sta!!.mus0 != null) {
                    lop.setEnabled(true)
                    getMusTime(sta!!.mus0, lop)
                } else {
                    lop.setText("00:00.000")
                    sta!!.loop0 = 0
                    lop.setEnabled(false)
                }
            }
            if (jtf === jm1) {
                sta!!.mus1 = `val`
                if (sta!!.mus1 != null) {
                    lop1.setEnabled(true)
                    getMusTime(sta!!.mus1, lop1)
                } else {
                    lop1.setText("00:00.000")
                    sta!!.loop1 = 0
                    lop1.setEnabled(false)
                }
            }
        }
        bvp = null
        cvp = null
        mp = null
    }

    override fun resized(x: Int, y: Int) {
        val w = 1400 / 8
        Page.Companion.set(name, x, y, 0, 0, w * 2, 50)
        Page.Companion.set(hea, x, y, 0, 50, w, 50)
        Page.Companion.set(jhea, x, y, w, 50, w, 50)
        Page.Companion.set(len, x, y, w * 2, 50, w, 50)
        Page.Companion.set(jlen, x, y, w * 3, 50, w, 50)
        Page.Companion.set(max, x, y, w * 4, 50, w, 50)
        Page.Companion.set(jmax, x, y, w * 5, 50, w, 50)
        Page.Companion.set(con, x, y, w * 6, 50, w, 50)
        Page.Companion.set(bg, x, y, 0, 100, w, 50)
        Page.Companion.set(jbg, x, y, w, 100, w, 50)
        Page.Companion.set(cas, x, y, w * 2, 100, w, 50)
        Page.Companion.set(jcas, x, y, w * 3, 100, w, 50)
        Page.Companion.set(mus, x, y, 0, 150, w, 50)
        Page.Companion.set(jm0, x, y, w, 150, w, 50)
        Page.Companion.set(loop, x, y, w * 2, 150, w, 50)
        Page.Companion.set(lop, x, y, w * 3, 150, w, 50)
        Page.Companion.set(jmh, x, y, w * 4, 150, w, 50)
        Page.Companion.set(jm1, x, y, w * 5, 150, w, 50)
        Page.Companion.set(loop1, x, y, w * 6, 150, w, 50)
        Page.Companion.set(lop1, x, y, w * 7, 150, w, 50)
        for (i in 0..3) Page.Companion.set(star[i], x, y, w * (2 + i), 0, w, 50)
        Page.Companion.set(lt, x, y, 0, 200, 1400, 100)
        lt.componentResized(x, y)
    }

    fun setData(st: Stage?) {
        sta = st
        abler(st != null)
        if (st == null) return
        change(true)
        name.setText(st.toString())
        jhea.setText("" + st.health)
        jlen.setText("" + st.len)
        jbg.setText("" + st.bg)
        jcas.setText("" + st.castle)
        jm0.setText("" + st.mus0)
        jmh.setText("<" + st.mush + "% health:")
        jm1.setText("" + st.mus1)
        jmax.setText("" + st.max)
        con.setSelected(!st.non_con)
        val str: String = Page.Companion.get(1, "star") + ": "
        for (i in 0..3) if (i < st.map.stars.size) star[i].setText(i + 1 + str + st.map.stars[i] + "%") else star[i].setText(i + 1 + str + "/")
        val lim = st.lim
        lt.setLimit(lim)
        change(false)
        lop.setText(convertTime(sta!!.loop0))
        lop1.setText(convertTime(sta!!.loop1))
        if (sta!!.mus0 != null) {
            lop.setEnabled(true)
            getMusTime(sta!!.mus0, lop)
        } else {
            lop.setText("00:00.000")
            lop.setToolTipText("No music")
            sta!!.loop0 = 0
            lop.setEnabled(false)
        }
        if (sta!!.mus1 != null) {
            lop1.setEnabled(true)
            getMusTime(sta!!.mus1, lop1)
        } else {
            lop1.setText("00:00.000")
            lop1.setToolTipText("No music")
            sta!!.loop1 = 0
            lop1.setEnabled(false)
        }
    }

    private fun abler(b: Boolean) {
        bg.setEnabled(b)
        cas.setEnabled(b)
        name.setEnabled(b)
        jhea.setEnabled(b)
        jlen.setEnabled(b)
        jbg.setEnabled(b)
        jcas.setEnabled(b)
        jmax.setEnabled(b)
        con.setEnabled(b)
        mus.setEnabled(b)
        jm0.setEnabled(b)
        jmh.setEnabled(b)
        jm1.setEnabled(b)
        for (jtf in star) jtf.setEnabled(b)
        lt.abler(b)
    }

    private fun addListeners() {
        bg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                bvp = BGViewPage(front, pac.desc.id, sta!!.bg)
                changePanel(bvp)
            }
        })
        cas.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                cvp = CastleViewPage(front, CastleList.Companion.from(sta), sta!!.castle)
                changePanel(cvp)
            }
        })
        mus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                mp = MusicPage(front, pac.desc.id)
                changePanel(mp)
            }
        })
        con.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                sta!!.non_con = !con.isSelected()
                setData(sta)
            }
        })
    }

    private fun convertTime(milli: Long): String {
        var min = milli / 60 / 1000
        var time = milli - min.toDouble() * 60000
        time /= 1000.0
        val df = DecimalFormat("#.###")
        var s: Double = df.format(time).toDouble()
        if (s >= 60) {
            s -= 60.0
            min += 1
        }
        return if (s < 10) {
            min.toString() + ":" + "0" + df.format(s)
        } else {
            min.toString() + ":" + df.format(s)
        }
    }

    private fun getMusTime(mus1: PackData.Identifier<Music>, jtf: JTF) {
        val f: Music? = mus1.get()
        if (f == null || f.data == null) {
            jtf.setToolTipText("Music not found")
            return
        }
        try {
            val duration: Long = CommonStatic.def.getMusicLength(f)
            if (duration == -1L) {
                jtf.setToolTipText("Invalid Format")
            } else if (duration == -2L) {
                jtf.setToolTipText("Unsupported Format")
            } else if (duration == -3L) {
                jtf.setToolTipText("Can't get duration")
            } else if (duration >= 0) {
                jtf.setToolTipText(convertTime(duration))
            } else {
                jtf.setToolTipText("Unknown error $duration")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun ini() {
        set(hea)
        set(len)
        set(max)
        add(bg)
        add(cas)
        add(con)
        add(mus)
        set(jhea)
        set(jlen)
        set(jbg)
        set(jcas)
        set(jmax)
        set(name)
        set(jm0)
        set(jmh)
        set(jm1)
        add(lt)
        set(loop)
        set(lop)
        set(loop1)
        set(lop1)
        con.setSelected(true)
        for (i in 0..3) set(JTF().also { star[i] = it })
        addListeners()
        abler(false)
    }

    private fun input(jtf: JTF, str: String) {
        var str = str
        if (sta == null) return
        if (jtf === name) {
            str = str.trim { it <= ' ' }
            if (str.length > 0) sta!!.name = str
            for (r in Recd.Companion.map.values) if (r.st === sta) r.marked = true
            return
        }
        var `val`: Int = CommonStatic.parseIntN(str)
        if (jtf === jhea) {
            if (`val` <= 0) return
            sta!!.health = `val`
        }
        if (jtf === jlen) {
            if (`val` > 8000) `val` = 8000
            if (`val` < 2000) `val` = 2000
            sta!!.len = `val`
        }
        if (jtf === jmax) {
            if (`val` <= 0 || `val` > 50) return
            sta!!.max = `val`
        }
        for (i in 0..3) if (jtf === star[i]) {
            val strs = str.split(" ").toTypedArray()
            val vals: IntArray = CommonStatic.parseIntsN(strs[strs.size - 1])
            `val` = if (vals.size == 0) -1 else vals[vals.size - 1]
            val sr = sta!!.map.stars
            if (i == 0 && `val` <= 0) `val` = 100
            if (i < sr.size) if (`val` > 0) sr[i] = `val` else sta!!.map.stars = Arrays.copyOf(sr, i) else if (`val` > 0) {
                val ans = IntArray(i + 1)
                for (j in 0 until i) if (j < sr.size) ans[j] = sr[j] else ans[j] = sr[sr.size - 1]
                ans[i] = `val`
                sta!!.map.stars = ans
            }
        }
        if (jtf === jmh) sta!!.mush = `val`
        if (jtf === lop) {
            val tim = toMilli(jtf.getText())
            if (tim != -1L) {
                sta!!.loop0 = tim
            }
            lop.setText(convertTime(sta!!.loop0))
        }
        if (jtf === lop1) {
            val tim = toMilli(jtf.getText())
            if (tim != -1L) {
                sta!!.loop1 = tim
            }
            lop1.setText(convertTime(sta!!.loop1))
        }
    }

    private fun set(jl: JLabel) {
        jl.horizontalAlignment = SwingConstants.CENTER
        jl.border = BorderFactory.createEtchedBorder()
        add(jl)
    }

    private fun set(jtf: JTF) {
        add(jtf)
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusGained(fe: FocusEvent?) {
                if (isAdj) return
                if (jtf === jm0) musl = 0
                if (jtf === jm1) musl = 1
            }

            override fun focusLost(fe: FocusEvent?) {
                if (isAdj) return
                input(jtf, jtf.getText())
                setData(sta)
            }
        })
    }

    private fun toMilli(time: String): Long {
        return try {
            val times: LongArray = CommonStatic.parseLongsN(time)
            for (t in times) {
                if (t < 0) {
                    return -1
                }
            }
            if (times.size == 1) {
                times[0] * 1000
            } else if (times.size == 2) {
                (times[0] * 60 + times[1]) * 1000
            } else if (times.size == 3) {
                if (times[2] < 1000) {
                    (times[0] * 60 + times[1]) * 1000 + times[2]
                } else {
                    val decimal = java.lang.Long.toString(times[2]).substring(0, 3)
                    (times[0] * 60 + times[1]) * 1000 + decimal.toInt()
                }
            } else {
                -1
            }
        } catch (e: Exception) {
            -1
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pac = pack
        lt = LimitTable(p, this, pac)
        ini()
    }
}