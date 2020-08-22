package page.packimport

import common.util.Data
import common.util.unit.Unit
import page.Page
import java.awt.event.ActionEvent
import java.util.*
import java.util.function.Consumer

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class CGLREditPage(p: Page?, pac: UserPack?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val jlcg: JList<CharaGroup> = JList<CharaGroup>()
    private val jlsb: JList<CharaGroup> = JList<CharaGroup>()
    private val jllr: JList<LvRestrict> = JList<LvRestrict>()
    private val jlus: JList<Unit> = JList<Unit>()
    private val jlua: JList<Unit> = JList<Unit>()
    private val jspcg: JScrollPane = JScrollPane(jlcg)
    private val jspsb: JScrollPane = JScrollPane(jlsb)
    private val jsplr: JScrollPane = JScrollPane(jllr)
    private val jspus: JScrollPane = JScrollPane(jlus)
    private val jspua: JScrollPane = JScrollPane(jlua)
    private val cgt: JBTN = JBTN(0, "include")
    private val addcg: JBTN = JBTN(0, "add")
    private val remcg: JBTN = JBTN(0, "rem")
    private val addus: JBTN = JBTN(0, "add")
    private val remus: JBTN = JBTN(0, "rem")
    private val addlr: JBTN = JBTN(0, "add")
    private val remlr: JBTN = JBTN(0, "rem")
    private val addsb: JBTN = JBTN(0, "add")
    private val remsb: JBTN = JBTN(0, "rem")
    private val jtfsb: JTF = JTF()
    private val jtfal: JTF = JTF()
    private val jtfra: Array<JTF?> = arrayOfNulls<JTF>(Data.Companion.RARITY_TOT)
    private val jtfna: JTF = JTF()
    private val jtflr: JTF = JTF()
    private val vuif: JBTN = JBTN(0, "vuif")
    private val pack: UserPack?
    private val lcg: FixIndexList<CharaGroup>
    private val llr: FixIndexList<LvRestrict>
    private var changing = false
    private var cg: CharaGroup? = null
    private var sb: CharaGroup? = null
    private var lr: LvRestrict? = null
    private var ufp: UnitFindPage? = null
    override fun renew() {
        if (ufp != null && ufp.getList() != null) {
            changing = true
            val list: MutableList<Unit> = ArrayList()
            for (f in ufp.getList()) if (!list.contains(f.unit)) list.add(f.unit)
            jlua.setListData(list.toTypedArray())
            jlua.clearSelection()
            if (list.size > 0) jlua.setSelectedIndex(0)
            changing = false
        }
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jspcg, x, y, 50, 100, 300, 800)
        Page.Companion.set(addcg, x, y, 50, 950, 150, 50)
        Page.Companion.set(remcg, x, y, 200, 950, 150, 50)
        Page.Companion.set(cgt, x, y, 400, 100, 300, 50)
        Page.Companion.set(jspus, x, y, 400, 200, 300, 700)
        Page.Companion.set(addus, x, y, 400, 950, 150, 50)
        Page.Companion.set(remus, x, y, 550, 950, 150, 50)
        Page.Companion.set(vuif, x, y, 750, 100, 300, 50)
        Page.Companion.set(jspua, x, y, 750, 200, 300, 700)
        Page.Companion.set(jsplr, x, y, 1100, 100, 300, 800)
        Page.Companion.set(addlr, x, y, 1100, 950, 150, 50)
        Page.Companion.set(remlr, x, y, 1250, 950, 150, 50)
        Page.Companion.set(jspsb, x, y, 1450, 100, 300, 800)
        Page.Companion.set(addsb, x, y, 1450, 950, 150, 50)
        Page.Companion.set(remsb, x, y, 1600, 950, 150, 50)
        Page.Companion.set(jtfal, x, y, 1800, 100, 400, 50)
        Page.Companion.set(jtfsb, x, y, 1800, 550, 400, 50)
        Page.Companion.set(jtfna, x, y, 50, 900, 300, 50)
        Page.Companion.set(jtflr, x, y, 1100, 900, 300, 50)
        for (i in jtfra.indices) Page.Companion.set(jtfra[i], x, y, 1800, 200 + 50 * i, 400, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        vuif.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (ufp == null) ufp = UnitFindPage(getThis(), pack.desc.id)
                changePanel(ufp)
            }
        })
    }

    private fun `addListeners$CG`() {
        addcg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                cg = CharaGroup(pack.getNextID<CharaGroup, CharaGroup>(CharaGroup::class.java))
                lcg.add(cg)
                updateCGL()
                jlcg.setSelectedValue(cg, true)
                changing = false
            }
        })
        remcg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (cg == null) return
                changing = true
                val list: MutableList<CharaGroup> = lcg.getList()
                var ind = list.indexOf(cg) - 1
                if (ind < 0 && list.size > 1) ind = 0
                list.remove(cg)
                lcg.remove(cg)
                cg = if (ind >= 0) list[ind] else null
                updateCGL()
                changing = false
            }
        })
        jlcg.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlcg.getValueIsAdjusting()) return
                changing = true
                cg = jlcg.getSelectedValue()
                updateCG()
                changing = false
            }
        })
        addus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val u: List<Unit> = jlua.getSelectedValuesList()
                if (cg == null || u.size == 0) return
                changing = true
                cg.set.addAll(u)
                updateCG()
                jlus.setSelectedValue(u[0], true)
                changing = false
            }
        })
        remus.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val u: Unit = jlus.getSelectedValue()
                if (cg == null || u == null) return
                changing = true
                val list: MutableList<Unit> = ArrayList()
                list.addAll(cg.set)
                var ind = list.indexOf(u) - 1
                if (ind < 0 && list.size > 1) ind = 0
                cg.set.remove(u)
                updateCG()
                jlus.setSelectedIndex(ind)
                changing = false
            }
        })
        cgt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (cg == null) return
                cg.type = 2 - cg.type
                cgt.setText(0, if (cg.type == 0) "include" else "exclude")
            }
        })
        jtfna.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val str: String = jtfna.getText()
            if (cg.name == str) return@setLnr
            cg.name = str
        })
    }

    private fun `addListeners$LR`() {
        addlr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                lr = LvRestrict(pack.getNextID<LvRestrict, LvRestrict>(LvRestrict::class.java))
                llr.add(lr)
                updateLRL()
                jllr.setSelectedValue(lr, true)
                changing = false
            }
        })
        remlr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (lr == null) return
                changing = true
                val list: MutableList<LvRestrict> = llr.getList()
                var ind = list.indexOf(lr) - 1
                if (ind < 0 && list.size > 1) ind = 0
                list.remove(lr)
                llr.remove(lr)
                lr = if (ind >= 0) list[ind] else null
                updateLRL()
                changing = false
            }
        })
        jllr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jllr.getValueIsAdjusting()) return
                changing = true
                lr = jllr.getSelectedValue()
                updateLR()
                changing = false
            }
        })
        addsb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val lv = intArrayOf(120, 10, 10, 10, 10, 10)
                lr.res.put(cg, lv)
                sb = cg
                updateLR()
                changing = false
            }
        })
        remsb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (sb == null) return
                changing = true
                var ind: Int = jlsb.getSelectedIndex()
                lr.res.remove(sb)
                updateLR()
                if (lr.res.size >= ind) ind = lr.res.size - 1
                jlsb.setSelectedIndex(ind)
                sb = jlsb.getSelectedValue()
                updateSB()
                changing = false
            }
        })
        jlsb.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlsb.getValueIsAdjusting()) return
                changing = true
                sb = jlsb.getSelectedValue()
                updateSB()
                changing = false
            }
        })
        jtflr.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val str: String = jtflr.getText()
            if (lr.name == str) return@setLnr
            lr.name = str
        })
    }

    private fun ini() {
        add(back)
        add(jspcg)
        add(addcg)
        add(remcg)
        add(jspus)
        add(addus)
        add(remus)
        add(jsplr)
        add(addlr)
        add(remlr)
        add(jspsb)
        add(addsb)
        add(remsb)
        add(vuif)
        add(jspua)
        add(cgt)
        set(jtfsb)
        set(jtfal)
        set(jtfna)
        set(jtflr)
        for (i in jtfra.indices) set(JTF().also { jtfra[i] = it })
        jlus.setCellRenderer(UnitLCR())
        jlua.setCellRenderer(UnitLCR())
        updateCGL()
        updateLRL()
        addListeners()
        `addListeners$CG`()
        `addListeners$LR`()
    }

    private fun put(tar: IntArray, `val`: IntArray) {
        for (i in 0 until Math.min(tar.size, `val`.size)) tar[i] = `val`[i]
    }

    private fun set(jtf: JTF) {
        add(jtf)
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                val inp: IntArray = CommonStatic.parseIntsN(jtf.getText())
                for (i in inp.indices) if (inp[i] < 0) inp[i] = 0
                if (jtf === jtfal) put(lr.all, inp)
                if (jtf === jtfsb) put(lr.res.get(sb), inp)
                for (i in jtfra.indices) if (jtf === jtfra[i]) put(lr.rares.get(i), inp)
                updateSB()
            }
        })
    }

    private operator fun set(jtf: JTF?, str: String, lvs: IntArray?) {
        var str: String? = str
        if (lvs != null) {
            str += "Lv." + lvs[0] + " {"
            for (i in 1..4) str += lvs[i].toString() + ","
            str += lvs[5].toString() + "}"
        }
        jtf.setText(str)
    }

    private fun updateCG() {
        jlus.setEnabled(cg != null)
        addus.setEnabled(cg != null)
        remus.setEnabled(cg != null)
        remcg.setEnabled(cg != null && !cg.used())
        cgt.setEnabled(cg != null)
        jtfna.setEnabled(cg != null)
        cgt.setText("")
        jtfna.setText("")
        addsb.setEnabled(lr != null && cg != null && !lr.res.containsKey(cg))
        if (cg == null) jlus.setListData(arrayOfNulls<Unit>(0)) else {
            jlus.setListData(cg.set.toTypedArray())
            cgt.setText(0, if (cg.type == 0) "include" else "exclude")
            jtfna.setText(cg.name)
        }
    }

    private fun updateCGL() {
        jlcg.setListData(lcg.getList().toTypedArray())
        jlcg.setSelectedValue(cg, true)
        updateCG()
    }

    private fun updateLR() {
        remlr.setEnabled(lr != null && !lr.used())
        jlsb.setEnabled(lr != null)
        addsb.setEnabled(lr != null && cg != null && !lr.res.containsKey(cg))
        jtflr.setEnabled(lr != null)
        jtflr.setText("")
        if (lr == null) jlsb.setListData(arrayOfNulls<CharaGroup>(0)) else {
            jlsb.setListData(lr.res.keys.toTypedArray())
            jtflr.setText(lr.name)
        }
        if (lr == null || sb == null || !lr.res.containsKey(sb)) sb = null
        jlsb.setSelectedValue(sb, true)
        jtfal.setEnabled(lr != null)
        for (jtf in jtfra) jtf.setEnabled(lr != null)
        updateSB()
    }

    private fun updateLRL() {
        jllr.setListData(llr.getList().toTypedArray())
        jllr.setSelectedValue(lr, true)
        updateLR()
    }

    private fun updateSB() {
        jtfsb.setEnabled(sb != null)
        if (lr != null) {
            set(jtfal, "all: ", lr.all)
            for (i in jtfra.indices) set(jtfra[i], Interpret.RARITY.get(i).toString() + ": ", lr.rares.get(i))
        } else {
            set(jtfal, "all: ", null)
            for (i in jtfra.indices) set(jtfra[i], Interpret.RARITY.get(i).toString() + ": ", null)
        }
        if (lr == null || sb == null) set(jtfsb, "group: ", null) else set(jtfsb, "group: ", lr.res.get(sb))
        remsb.setEnabled(sb != null)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pack = pac
        lcg = pack.groups
        llr = pack.lvrs
        ini()
        resized()
    }
}