package page.basisimport

import common.system.Node
import common.util.unit.Form
import page.Page
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class BasisPage(p: Page?) : LubCont(p) {
    private val back: JBTN = JBTN(0, "back")
    private val unit: JBTN = JBTN(0, "vuif")
    private val setc: JBTN = JBTN(0, "set0")
    private val bsadd: JBTN = JBTN(0, "add")
    private val bsrem: JBTN = JBTN(0, "rem")
    private val bscop: JBTN = JBTN(0, "copy")
    private val badd: JBTN = JBTN(0, "add")
    private val brem: JBTN = JBTN(0, "rem")
    private val form: JBTN = JBTN(0, "form")
    private val bsjtf: JTF = JTF()
    private val bjtf: JTF = JTF()
    private val lvjtf: JTF = JTF()
    private val lvorb: JBTN = JBTN(0, "orb")
    private val pcoin = JLabel()
    private val vbs: Vector<BasisSet> = Vector<BasisSet>(BasisSet.Companion.list())
    private val jlbs: ReorderList<BasisSet> = ReorderList<BasisSet>(vbs)
    private val jspbs: JScrollPane = JScrollPane(jlbs)
    private val vb: Vector<BasisLU> = Vector<BasisLU>()
    private val jlb: ReorderList<BasisLU> = ReorderList<BasisLU>(vb, BasisLU::class.java, "lineup")
    private val jspb: JScrollPane = JScrollPane(jlb)
    private val jlcs: JList<String> = JList<String>(Interpret.COMF)
    private val jspcs: JScrollPane = JScrollPane(jlcs)
    private val jlcl: JList<String> = JList<String>()
    private val jspcl: JScrollPane = JScrollPane(jlcl)
    private val jlc: ComboListTable = ComboListTable(this, lu())
    private val jspc: JScrollPane = JScrollPane(jlc)
    private val jlcn: ComboList = ComboList()
    private val jspcn: JScrollPane = JScrollPane(jlcn)
    private val lub: LineUpBox = LineUpBox(this)
    private val ul: JList<Form> = JList<Form>()
    private val jspul: JScrollPane = JScrollPane(ul)
    private val ncb: NyCasBox = NyCasBox()
    private val jbcs: Array<JBTN?> = arrayOfNulls<JBTN>(3)
    private var changing = false
    private var outside = false
    private var ufp: UnitFLUPage? = null
    private val trea: TreaTable = TreaTable(this, BasisSet.Companion.current())
    override fun callBack(o: Any?) {
        if (o == null) changeLU() else if (o is Form) {
            val f = o
            val u = f.unit
            setLvs(f)
            val lc: List<Combo> = u.allCombo()
            if (lc.size == 0) return
            outside = true
            changing = true
            jlc.setList(lc)
            jlc.getSelectionModel().setSelectionInterval(0, 0)
            setC(0)
            changing = false
        }
    }

    protected override fun getLub(): LineUpBox {
        return lub
    }

    protected override fun keyTyped(e: KeyEvent) {
        if (trea.hasFocus()) return
        if (lvjtf.hasFocus()) return
        if (bjtf.hasFocus()) return
        if (bsjtf.hasFocus()) return
        super.keyTyped(e)
        e.consume()
    }

    protected override fun mouseClicked(e: MouseEvent) {
        if (e.source === jlc) jlc.clicked(e.point)
        super.mouseClicked(e)
    }

    protected override fun renew() {
        if (ufp != null) {
            val lf: List<Form> = ufp.getList()
            if (lf != null) ul.setListData(Node.Companion.deRep(lf).toTypedArray())
        }
    }

    protected override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspbs, x, y, 50, 100, 200, 300)
        Page.Companion.set(bsadd, x, y, 50, 450, 200, 50)
        Page.Companion.set(bsrem, x, y, 50, 550, 200, 50)
        Page.Companion.set(bscop, x, y, 50, 650, 200, 50)
        Page.Companion.set(bsjtf, x, y, 50, 750, 200, 50)
        Page.Companion.set(jspb, x, y, 750, 50, 200, 500)
        Page.Companion.set(badd, x, y, 750, 550, 200, 50)
        Page.Companion.set(brem, x, y, 750, 600, 200, 50)
        Page.Companion.set(bjtf, x, y, 750, 700, 200, 50)
        Page.Companion.set(ncb, x, y, 750, 800, 200, 300)
        Page.Companion.set(trea, x, y, 300, 50, 400, 1200)
        Page.Companion.set(lub, x, y, 1000, 100, 600, 300)
        Page.Companion.set(unit, x, y, 1650, 0, 200, 50)
        Page.Companion.set(jspcs, x, y, 1950, 0, 300, 250)
        Page.Companion.set(jspcl, x, y, 1950, 300, 300, 450)
        Page.Companion.set(jspc, x, y, 1050, 800, 1200, 450)
        Page.Companion.set(jspcn, x, y, 1300, 450, 300, 300)
        Page.Companion.set(jspul, x, y, 1650, 50, 200, 700)
        Page.Companion.set(form, x, y, 1000, 400, 200, 50)
        Page.Companion.set(pcoin, x, y, 1000, 0, 600, 50)
        Page.Companion.set(lvjtf, x, y, 1000, 50, 350, 50)
        Page.Companion.set(lvorb, x, y, 1350, 50, 250, 50)
        Page.Companion.set(setc, x, y, 1050, 700, 200, 50)
        for (i in 0..2) Page.Companion.set(jbcs[i], x, y, 750, 1100 + 50 * i, 200, 50)
        jlc.setRowHeight(85)
        jlc.getColumnModel().getColumn(1).setPreferredWidth(Page.Companion.size(x, y, 300))
    }

    protected override fun timer(t: Int) {
        ncb.paint(ncb.getGraphics())
        super.timer(t)
    }

    private fun `addListeners$0`() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(getFront())
            }
        })
        unit.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                if (ufp == null) ufp = UnitFLUPage(getThis())
                changePanel(ufp)
            }
        })
        ul.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                changing = true
                lub.select(ul.getSelectedValue())
                changing = false
            }
        })
        form.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                lub.adjForm()
                changeLU()
            }
        })
        lvjtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                val lv: IntArray = CommonStatic.parseIntsN(lvjtf.getText())
                lub.setLv(lv)
                if (lub.sf != null) setLvs(lub.sf)
            }
        })
        lvorb.setLnr(Consumer { x: ActionEvent? ->
            if (lub.sf != null) {
                changePanel(LevelEditPage(this, lu().getLv(lub.sf.unit), lub.sf))
            }
        })
        for (i in 0..2) {
            jbcs[i].addActionListener(object : ActionListener {
                override fun actionPerformed(e: ActionEvent?) {
                    BasisSet.Companion.current().sele.nyc.get(i)++
                    BasisSet.Companion.current().sele.nyc.get(i) %= NyCastle.Companion.TOT
                }
            })
        }
    }

    private fun `addListeners$1`() {
        jlbs.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (jlb.getValueIsAdjusting() || changing) return
                changing = true
                if (jlbs.getSelectedValue() == null) jlbs.setSelectedValue(BasisSet.Companion.current(), true) else setBS(jlbs.getSelectedValue())
                changing = false
            }
        })
        jlb.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (jlb.getValueIsAdjusting() || changing) return
                changing = true
                if (jlb.getSelectedValue() == null) jlb.setSelectedValue(BasisSet.Companion.current().sele, true) else setB(jlb.getSelectedValue())
                changing = false
            }
        })
        jlbs.list = object : ReorderListener<BasisSet?> {
            override fun reordered(ori: Int, fin: Int) {
                changing = false
                val l: List<BasisSet> = BasisSet.Companion.list()
                val b: BasisSet = l.removeAt(ori)
                l.add(fin, b)
            }

            override fun reordering() {
                changing = true
            }
        }
        jlb.list = object : ReorderListener<BasisLU?> {
            override fun add(blu: BasisLU?): Boolean {
                BasisSet.Companion.current().lb.add(blu)
                return true
            }

            override fun reordered(ori: Int, fin: Int) {
                changing = false
                val l: List<BasisLU> = BasisSet.Companion.current().lb
                val b: BasisLU = l.removeAt(ori)
                l.add(fin, b)
            }

            override fun reordering() {
                changing = true
            }
        }
        bsadd.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b = BasisSet()
                vbs.clear()
                vbs.addAll(BasisSet.Companion.list())
                jlbs.setListData(vbs)
                jlbs.setSelectedValue(b, true)
                setBS(b)
                changing = false
            }
        })
        bsrem.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                BasisSet.Companion.list().remove(BasisSet.Companion.current())
                vbs.clear()
                vbs.addAll(BasisSet.Companion.list())
                jlbs.setListData(vbs)
                val b: BasisSet = BasisSet.Companion.list().get(BasisSet.Companion.list().size - 1)
                jlbs.setSelectedValue(b, true)
                setBS(b)
                changing = false
            }
        })
        bscop.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b = BasisSet(BasisSet.Companion.current())
                vbs.clear()
                vbs.addAll(BasisSet.Companion.list())
                jlbs.setListData(vbs)
                jlbs.setSelectedValue(b, true)
                setBS(b)
                changing = false
            }
        })
        badd.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b: BasisLU = BasisSet.Companion.current().add()
                vb.clear()
                vb.addAll(BasisSet.Companion.current().lb)
                jlb.setListData(vb)
                jlb.setSelectedValue(b, true)
                setB(b)
                changing = false
            }
        })
        brem.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val b: BasisLU = BasisSet.Companion.current().remove()
                vb.clear()
                vb.addAll(BasisSet.Companion.current().lb)
                jlb.setListData(vb)
                jlb.setSelectedValue(b, true)
                setB(b)
                changing = false
            }
        })
        bsjtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                val str: String = bsjtf.getText().trim { it <= ' ' }
                if (str.length > 0) BasisSet.Companion.current().name = str
                bsjtf.setText(BasisSet.Companion.current().name)
                jlbs.repaint()
            }
        })
        bjtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                val str: String = bjtf.getText().trim { it <= ' ' }
                if (str.length > 0) BasisSet.Companion.current().sele.name = str
                bjtf.setText(BasisSet.Companion.current().sele.name)
                jlb.repaint()
            }
        })
    }

    private fun `addListeners$2`() {
        jlcs.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent) {
                if (changing || e.getValueIsAdjusting()) return
                changing = true
                if (jlcs.getSelectedValue() == null) jlcs.setSelectedIndex(0)
                setCS(jlcs.getSelectedIndex())
                changing = false
            }
        })
        jlcl.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent) {
                if (changing || e.getValueIsAdjusting()) return
                changing = true
                setCL(jlcs.getSelectedIndex())
                changing = false
            }
        })
        jlcn.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent) {
                if (changing || e.getValueIsAdjusting()) return
                changing = true
                setCN()
                changing = false
            }
        })
        jlc.getSelectionModel().addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                changing = true
                setC(jlc.getSelectedRow())
                changing = false
            }
        })
        setc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                lu().set(jlc.list.get(jlc.getSelectedRow()).units)
                changeLU()
            }
        })
    }

    private fun changeLU() {
        jlcn.setListData(lu().coms.toTypedArray())
        setCN()
        updateSetC()
        lub.updateLU()
        setLvs(lub.sf)
    }

    private fun ini() {
        add(back)
        add(jspbs)
        add(jspb)
        add(trea)
        add(trea)
        add(bsadd)
        add(bsrem)
        add(bscop)
        add(badd)
        add(brem)
        add(lub)
        add(back)
        add(unit)
        add(jspcs)
        add(jspcl)
        add(jspc)
        add(jspcn)
        add(lub)
        add(jspul)
        add(setc)
        add(bsjtf)
        add(bjtf)
        add(form)
        add(lvjtf)
        add(pcoin)
        add(lvorb)
        add(ncb)
        add(JBTN(0, "ctop").also { jbcs[0] = it })
        add(JBTN(0, "cmid").also { jbcs[1] = it })
        add(JBTN(0, "cbas").also { jbcs[2] = it })
        ul.setCellRenderer(UnitLCR())
        val m0: Int = ListSelectionModel.SINGLE_SELECTION
        val m1: Int = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        jlbs.setSelectedValue(BasisSet.Companion.current(), true)
        jlcs.setSelectedIndex(0)
        jlbs.setSelectionMode(m0)
        jlb.setSelectionMode(m0)
        jlcs.setSelectionMode(m0)
        jlcl.setSelectionMode(m1)
        jlcn.setSelectionMode(m0)
        jlc.getSelectionModel().setSelectionMode(m0)
        setCS(0)
        setBS(BasisSet.Companion.current())
        lub.setLU(lu())
        bsjtf.setText(BasisSet.Companion.current().name)
        bjtf.setText(BasisSet.Companion.current().sele.name)
        changeLU()
        `addListeners$0`()
        `addListeners$1`()
        `addListeners$2`()
        lvorb.setEnabled(lub.sf != null)
    }

    private fun lu(): LineUp {
        return BasisSet.Companion.current().sele.lu
    }

    private fun setB(b: BasisLU) {
        BasisSet.Companion.current().sele = b
        lub.setLU(if (b == null) null else b.lu)
        brem.setEnabled(BasisSet.Companion.current().lb.size > 1)
        bjtf.setText(BasisSet.Companion.current().sele.name)
        ncb.set(b.nyc)
        changeLU()
        callBack(lub.sf)
    }

    private fun setBS(bs: BasisSet) {
        BasisSet.Companion.setCurrent(bs)
        vb.clear()
        vb.addAll(bs.lb)
        jlb.setListData(vb)
        val b: BasisLU = bs.sele
        jlb.setSelectedValue(b, true)
        trea.setBasis(bs)
        bsjtf.setText(BasisSet.Companion.current().name)
        bsrem.setEnabled(BasisSet.Companion.current() !== BasisSet.Companion.def())
        setB(b)
    }

    private fun setC(c: Int) {
        if (outside) {
            jlcs.setSelectedIndex(0)
            jlcl.setListData(Interpret.getComboFilter(0))
            val row: Int = jlc.list.get(c).type
            jlcl.setSelectedIndex(row)
            val p: Point = jlcl.indexToLocation(row)
            val h: Int = jlcl.indexToLocation(1).y - jlcl.indexToLocation(0).y
            if (p != null) jlcl.scrollRectToVisible(Rectangle(p.x, p.y, 1, h))
        }
        updateSetC()
    }

    private fun setCL(cs: Int) {
        val cls: IntArray = jlcl.getSelectedIndices()
        if (cls.size == 0) {
            val lc: MutableList<Combo> = ArrayList<Combo>()
            for (i in 0 until CommonStatic.getBCAssets().filter.get(cs).length) for (c in CommonStatic.getBCAssets().combos.get(CommonStatic.getBCAssets().filter.get(cs).get(i))) lc.add(c)
            jlc.setList(lc)
        } else {
            val lc: MutableList<Combo> = ArrayList<Combo>()
            for (`val` in cls) for (c in CommonStatic.getBCAssets().combos.get(CommonStatic.getBCAssets().filter.get(cs).get(`val`))) lc.add(c)
            jlc.setList(lc)
        }
        jlc.getSelectionModel().setSelectionInterval(0, 0)
        outside = false
        setC(0)
    }

    private fun setCN() {
        lub.select(jlcn.getSelectedValue())
    }

    private fun setCS(cs: Int) {
        jlcl.setListData(Interpret.getComboFilter(cs))
        jlcl.setSelectedIndex(0)
        setCL(cs)
    }

    private fun setLvs(f: Form?) {
        lvorb.setEnabled(f != null)
        if (f == null) {
            lvjtf.setText("")
            pcoin.text = ""
            return
        }
        lvorb.setVisible(f.orbs != null)
        val strs: Array<String> = UtilPC.lvText(f, lu().getLv(f.unit).getLvs())
        lvjtf.setText(strs[0])
        pcoin.text = strs[1]
    }

    private fun updateSetC() {
        val com: Combo? = if (jlc.list.size > 0) jlc.list.get(jlc.getSelectedRow()) else null
        setc.setEnabled(com != null && !lu().contains(com))
        var b = false
        if (com != null) b = lu().willRem(com)
        setc.setForeground(if (b) Color.RED else Color.BLACK)
        setc.setText(0, "set" + if (b) "1" else "0")
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}