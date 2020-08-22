package page.battleimport

import common.battle.entity.Entity
import common.pack.PackData
import common.util.Data
import common.util.Res
import common.util.unit.Form
import java.awt.Point

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
interface BattleBox {
    open class BBPainter(protected val page: OuterBox, bas: BattleField, bb: BattleBox) : BattleConst {
        val bf: BattleField
        var pt = -1
        protected val box: BattleBox
        protected var siz = 0.0
        protected var corr = 0.0
        protected var unir // siz = pix/p;
                = 0.0
        private var sb: StageBasis? = null
        private val maxW: Int
        private val maxH: Int
        private val minH // in p
                : Int
        private var pos = 0
        private var midh = 0
        private var prew = 0
        private var preh // in pix
                = 0
        private var mouse // in pix
                : P? = null
        private val aux: BCAuxAssets = CommonStatic.getBCAssets()
        open fun click(p: Point?, button: Int) {}
        fun draw(g: FakeGraphics) {
            val w = box.getWidth()
            val h = box.getHeight()
            sb = bf.sb
            if (prew != w || preh != h) {
                clear()
                prew = w
                preh = h
            }
            regulate()
            ImgCore.Companion.set(g)
            val rect = P(box.getWidth(), box.getHeight())
            sb.bg.draw(g, rect, pos, midh, siz)
            drawCastle(g)
            drawEntity(g)
            drawBtm(g)
            drawTop(g)
            sb = null
        }

        fun getX(x: Double): Double {
            return (x * BattleConst.Companion.ratio + off) * siz + pos
        }

        fun regulate() {
            val w = box.getWidth()
            val h = box.getHeight()
            if (siz * minH > h * bar / 10) siz = 1.0 * h * bar / 10 / minH
            if (siz * maxH < h) siz = 1.0 * h / maxH
            if (siz * maxW < w) siz = 1.0 * w / maxW
            if (pos > 0) pos = 0
            if (maxW * siz + pos < w) pos = (w - maxW * siz).toInt()
            midh = h * bar / 10
            if (midh > siz * minH * 2) midh = (siz * minH * 2).toInt()
        }

        fun reset() {
            pt = bf.sb.time
            box.reset()
        }

        private fun adjust(w: Int, s: Int) {
            pos += w
            siz *= Math.pow(exp, s.toDouble())
        }

        private fun clear() {
            pt = -1
            siz = 0.0
            pos = 0
            midh = 0
        }

        @Synchronized
        fun drag(p: Point) {
            if (mouse != null) {
                val temp: P = PP(p)
                adjust((temp.x - mouse.x) as Int, 0)
                mouse.setTo(temp)
                reset()
            }
        }

        private fun drawBtm(g: FakeGraphics) {
            val w = box.getWidth()
            val h = box.getHeight()
            var cw = 0
            val time: Int = sb.time / 5 % 2
            var mtype = if (sb.mon < sb.next_lv) 0 else if (time == 0) 1 else 2
            if (sb.work_lv == 8) mtype = 2
            val left: FakeImage = aux.battle.get(0).get(mtype).getImg()
            val ctype = if (sb.can == sb.max_can && time == 0) 1 else 0
            val right: FakeImage = aux.battle.get(1).get(ctype).getImg()
            cw += left.getWidth()
            cw += right.getWidth()
            cw += aux.slot.get(0).getImg().getWidth() * 5
            val r = 1.0 * w / cw
            val avah = h * (10 - bar) / 10.toDouble()
            var hr: Double = avah / left.getHeight()
            hr = Math.min(r, hr)
            corr = hr
            var ih = (hr * left.getHeight()) as Int
            var iw = (hr * left.getWidth()) as Int
            g.drawImage(left, 0.0, h - ih.toDouble(), iw.toDouble(), ih.toDouble())
            iw = (hr * right.getWidth()) as Int
            ih = (hr * right.getHeight()) as Int
            g.drawImage(right, w - iw.toDouble(), h - ih.toDouble(), iw.toDouble(), ih.toDouble())
            Res.getCost(sb.next_lv, mtype > 0, SymCoord(g, hr, hr * 5, h - hr * 5, 2))
            Res.getWorkerLv(sb.work_lv, mtype > 0, SymCoord(g, hr, hr * 5, h - hr * 130, 0))
            var hi = h
            var marg = 0.0
            if (ctype == 0) for (i in 0 until 10 * sb.can / sb.max_can) {
                val img: FakeImage = aux.battle.get(1).get(2 + i).getImg()
                iw = (hr * img.getWidth()) as Int
                ih = (hr * img.getHeight()) as Int
                marg += hr * img.getHeight() - ih
                if (marg > 0.5) {
                    marg--
                    ih++
                }
                hi -= ih
                g.drawImage(img, w - iw.toDouble(), hi.toDouble(), iw.toDouble(), ih.toDouble())
            }
            hr = avah / 2 / aux.slot.get(0).getImg().getHeight()
            hr = Math.min(r, hr)
            for (i in 0..9) {
                val f: Form = sb.b.lu.fs.get(i / 5).get(i % 5)
                val img: FakeImage = if (f == null) aux.slot.get(0).getImg() else f.anim.uni.img
                iw = (hr * img.getWidth()) as Int
                ih = (hr * img.getHeight()) as Int
                var x = (w - iw * 5) / 2 + iw * (i % 5)
                var y = h - ih * (2 - i / 5)
                g.drawImage(img, x.toDouble(), y.toDouble(), iw.toDouble(), ih.toDouble())
                if (f == null) continue
                val pri: Int = sb.elu.price.get(i / 5).get(i % 5)
                if (pri == -1) g.colRect(x, y, iw, ih, 255, 0, 0, 100)
                val cool: Int = sb.elu.cool.get(i / 5).get(i % 5)
                val b = pri > sb.mon || cool > 0
                if (b) g.colRect(x, y, iw, ih, 0, 0, 0, 100)
                if (sb.locks.get(i / 5).get(i % 5)) g.colRect(x, y, iw, ih, 0, 255, 0, 100)
                if (cool > 0) {
                    val dw = (hr * 10).toInt()
                    val dh = (hr * 12).toInt()
                    val cd: Double = 1.0 * cool / sb.elu.maxC.get(i / 5).get(i % 5)
                    val xw = (cd * (iw - dw * 2)).toInt()
                    g.colRect(x + iw - dw - xw, y + ih - dh * 2, xw, dh, 0, 0, 0, -1)
                    g.colRect(x + dw, y + ih - dh * 2, iw - dw * 2 - xw, dh, 100, 212, 255, -1)
                } else Res.getCost(pri, !b, SymCoord(g, hr, iw.let { x += it; x }, ih.let { y += it; y }, 3))
            }
            unir = hr
        }

        private fun drawCastle(gra: FakeGraphics) {
            val at: FakeTransform = gra.getTransform()
            val drawCast = sb.ebase is Entity
            var posy = (midh - road_h * siz).toInt()
            var posx = ((800 * BattleConst.Companion.ratio + off) * siz + pos) as Int
            if (!drawCast) {
                val cind: PackData.Identifier<CastleImg> = sb.st.castle
                val cast: VImg = cind.get().img
                val bimg: FakeImage = cast.getImg()
                val bw = (bimg.getWidth() * siz) as Int
                val bh = (bimg.getHeight() * siz) as Int
                gra.drawImage(bimg, posx - bw.toDouble(), posy - bh.toDouble(), bw.toDouble(), bh.toDouble())
            } else (sb.ebase as Entity).anim.draw(gra, P(posx, posy), siz * sprite)
            gra.setTransform(at)
            (posx -= castw * siz / 2).toInt()
            posy -= casth * siz.toInt()
            Res.getBase(sb.ebase, SymCoord(gra, siz, posx, posy, 0))
            posx = (((sb.st.len - 800) * BattleConst.Companion.ratio + off) * siz + pos)
            drawNyCast(gra, (midh - road_h * siz).toInt(), posx, siz, sb.nyc)
            (posx += castw * siz / 2).toInt()
            Res.getBase(sb.ubase, SymCoord(gra, siz, posx, posy, 1))
        }

        private fun drawEntity(gra: FakeGraphics) {
            val w = box.getWidth()
            val h = box.getHeight()
            val at: FakeTransform = gra.getTransform()
            val psiz = siz * sprite
            CommonStatic.getConfig().battle = true
            for (i in 0..9) {
                val dep = i * DEP
                for (e in sb.le) if (e.layer == i && (sb.s_stop == 0 || e.abi and Data.Companion.AB_TIMEI == 0)) {
                    gra.setTransform(at)
                    val p = getX(e.pos)
                    val y = midh - (road_h - dep) * siz
                    e.anim.draw(gra, P(p, y), psiz)
                    gra.setTransform(at)
                    e.anim.drawEff(gra, P(p, y), siz)
                }
                for (wc in sb.lw) if (wc.layer == i) {
                    gra.setTransform(at)
                    val p: Double = (wc.pos * BattleConst.Companion.ratio + off - wave) * siz + pos
                    val y: Double = midh - (road_h - DEP * wc.layer) * siz
                    wc.draw(gra, P(p, y), psiz)
                }
                for (eac in sb.lea) if (eac.layer == i) {
                    gra.setTransform(at)
                    val p = getX(eac.pos)
                    val y: Double = midh - (road_h - DEP * eac.layer) * siz
                    if (eac is WaprCont) {
                        val dx = if ((eac as WaprCont).dire == -1) -27 * siz else -24 * siz
                        eac.draw(gra, P(p + dx, y - 24 * siz), psiz)
                    } else {
                        eac.draw(gra, P(p, y), psiz)
                    }
                }
            }
            gra.setTransform(at)
            val can = cany[sb.canon.id]
            val disp = canx[sb.canon.id]
            var ori: P? = P(getX(sb.ubase.pos) + disp * siz, midh + (can - road_h) * siz)
            sb.canon.drawBase(gra, ori, psiz)
            gra.setTransform(at)
            ori = P(getX(sb.canon.pos), midh - road_h * siz)
            sb.canon.drawAtk(gra, ori, psiz)
            gra.setTransform(at)
            if (sb.sniper != null && sb.sniper.enabled) {
                ori = P(getX(sb.sniper.getPos()), midh - road_h * siz)
                sb.sniper.drawBase(gra, ori, psiz)
                gra.setTransform(at)
            }
            if (sb.s_stop > 0) {
                gra.setComposite(FakeGraphics.Companion.GRAY, 0, 0)
                gra.fillRect(0, 0, w, h)
                for (i in 0..9) {
                    val dep = i * DEP
                    for (e in sb.le) if (e.layer == i && e.abi and Data.Companion.AB_TIMEI > 0) {
                        gra.setTransform(at)
                        val p = getX(e.pos)
                        val y = midh - (road_h - dep) * siz
                        e.anim.draw(gra, P(p, y), psiz)
                        gra.setTransform(at)
                        e.anim.drawEff(gra, P(p, y), siz)
                    }
                }
            }
            gra.setTransform(at)
            CommonStatic.getConfig().battle = false
        }

        private fun drawTop(g: FakeGraphics) {
            val w = box.getWidth()
            val p: P = Res.getMoney(sb.mon as Int, sb.max_mon, SymCoord(g, 1, w, 0, 1))
            val ih = p.y as Int
            var n = 0
            var bimg: FakeImage = aux.battle.get(2).get(1).getImg()
            val cw: Int = bimg.getWidth()
            if (sb.conf.get(0) and 2 > 0) {
                bimg = aux.battle.get(2).get(if (sb.sniper.enabled) 2 else 4).getImg()
                g.drawImage(bimg, w - cw.toDouble(), ih.toDouble())
                n++
            }
            bimg = aux.battle.get(2).get(1).getImg()
            if (sb.conf.get(0) and 1 > 0) {
                g.drawImage(bimg, w - cw * (n + 1).toDouble(), ih.toDouble())
                n++
            }
            bimg = aux.battle.get(2).get(if (page.getSpeed() > 0) 0 else 3).getImg()
            for (i in 0 until Math.abs(page.getSpeed())) g.drawImage(bimg, w - cw * (i + 1 + n).toDouble(), ih.toDouble())
        }

        @Synchronized
        fun press(p: Point) {
            mouse = PP(p)
        }

        @Synchronized
        fun release(p: Point) {
            mouse = null
        }

        @Synchronized
        fun wheeled(p: Point, ind: Int) {
            val w = box.getWidth()
            val h = box.getHeight()
            val psiz = siz * Math.pow(exp, ind.toDouble())
            if (psiz * minH > h * bar / 10 || psiz * maxH < h || psiz * maxW < w) return
            val dif = (-((p.x - pos) * (Math.pow(exp, ind.toDouble()) - 1))).toInt()
            adjust(dif, ind)
            reset()
        }

        companion object {
            private const val exp = 0.9
            private const val sprite = 0.8
            private const val road_h = 156 // in p
            private const val off = 200
            private const val DEP = 4
            private const val bar = 8
            private const val wave = 28
            private const val castw = 128
            private const val casth = 256
            private const val c0y = -130
            private const val c1y = -130
            private const val c2y = -258
            private val cany = intArrayOf(-134, -134, -134, -250, -250, -134, -134, -134)
            private val canx = intArrayOf(0, 0, 0, 64, 64, 0, 0, 0)
            fun drawNyCast(gra: FakeGraphics, y: Int, x: Int, siz: Double, inf: IntArray) {
                val aux: BCAuxAssets = CommonStatic.getBCAssets()
                var bimg: FakeImage = aux.main.get(2).get(inf[2]).getImg()
                var bw: Int = bimg.getWidth()
                var bh: Int = bimg.getHeight()
                var cy = (y + c0y * siz).toInt()
                gra.drawImage(bimg, x.toDouble(), cy.toDouble(), (bw * siz) as Int.toDouble(), (bh * siz) as Int.toDouble())
                bimg = aux.main.get(0).get(inf[0]).getImg()
                bw = bimg.getWidth()
                bh = bimg.getHeight()
                cy = (y + c2y * siz).toInt()
                gra.drawImage(bimg, x.toDouble(), cy.toDouble(), (bw * siz) as Int.toDouble(), (bh * siz) as Int.toDouble())
                bimg = aux.main.get(1).get(inf[1]).getImg()
                bw = bimg.getWidth()
                bh = bimg.getHeight()
                cy = (y + c1y * siz).toInt()
                gra.drawImage(bimg, x.toDouble(), cy.toDouble(), (bw * siz) as Int.toDouble(), (bh * siz) as Int.toDouble())
            }
        }

        init {
            bf = bas
            box = bb
            maxW = (bas.sb.st.len * BattleConst.Companion.ratio + off * 2)
            maxH = 510 * 3
            minH = 510
        }
    }

    interface OuterBox : RetFunc {
        fun getSpeed(): Int
    }

    fun click(p: Point?, button: Int) {
        getPainter().click(p, button)
    }

    fun drag(p: Point) {
        getPainter().drag(p)
    }

    fun getHeight(): Int
    fun getPainter(): BBPainter
    fun getWidth(): Int
    fun paint()
    fun press(p: Point) {
        getPainter().press(p)
    }

    fun release(p: Point) {
        getPainter().release(p)
    }

    fun reset()
    fun wheeled(p: Point, ind: Int) {
        getPainter().wheeled(p, ind)
    }
}