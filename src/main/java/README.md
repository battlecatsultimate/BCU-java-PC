# Battle Cats Ultimate
###### A fan-made program featuring data and animations in The Battle Cats by PONOS, along with custom content.

## Installing BCU Version 5 for Windows, macOS, or Linux

1. Download and install [the JRE](https://www.java.com/en/download/manual.jsp) that corresponds to your operating system.
    - For macOS users, you might want to download [JDK 8](https://drive.google.com/file/d/1ZH6lJ5StHGPZB994oXej53KysDQzmKhc/view?usp=sharing) as well in the case that BCU fails to work properly, especially if JOGL is enabled.
2. Download [BCU-Initializer.jar](https://github.com/battlecatsultimate/bcu-assets/raw/master/jar/BCU-Initializer.jar)
3. Move the jar to a new, empty folder, then run the jar.

## Frequently Asked Questions
### How do I create custom content? / How do I animate?
Refer to the links below with the label "BCU Tutorial" under the **Links & Resources** section.

### Common Issues/Errors
If none of these apply to your situation, you can report it through GitHub Issues or the BCU Discord server, linked below. Make sure to include the most recent error log.

#### All Platforms
- *Another BCU is running or last BCU didn't close properly*
    - If the last BCU crashed, this will be the default message.
    - It's advised to avoid running two BCU instances at the same time, as well as allowing BCU to close properly.
    - If there was a real problem that seemed to have caused the crash, follow the above instructions to report the problem.
- *Not enough memory*
    - Open up BCU again. In the main menu, If the number on the top left says `# / 247 MB` and your computer is 64-bit, you installed the wrong Java. Install 64-bit Java instead.
    - If the above fails, you may need to use run.bat, which is available in the BCU Discord server, linked below.

#### macOS Issues
Some issues below can be fixed by running the jar via Terminal. If it suggests doing this, follow these instructions.
1. Open Launchpad and search up "Terminal", then open up the program.
2. Change the Terminal's current working directory to your BCU folder.
    1. Type `cd` into the Terminal, with a space after.
    2. Go to your finder and find the BCU folder.
    3. Drag and drop the BCU folder icon onto the Terminal window.
    4. Press the Enter key.
3. Run the jar through your terminal.
    1. Type `java -jar` into the Terminal, with a space after.
    2. In your BCU folder, locate the jar you will run.
    3. Drag and drop the jar onto the Terminal window.
    4. Press the Enter key.

- *The files downloaded are not in the BCU folder (For example, the `BCU_lib` folder was not downloaded automatically into your BCU folder)*
    - Follow the instructions above to use Terminal.
- *WARNING: NSWindow drag regions should only be invalidated on the Main Thread! This will throw an exception in the future.*
    - This usually happens only if JOGL is enabled. In order to keep JOGL on, make sure your JDK version is 8, then follow the instructions above to use Terminal.

## Links & Resources

- [BCU Tutorial - Animating](https://docs.google.com/document/d/1X2UZGEJzkSTXjgvozQRgKoHSjCpsHt4Eh94sllPu8JQ/edit?usp=sharing)
- [BCU Tutorial - Custom Content](https://docs.google.com/document/d/1k682-1xNnHH3Z4sq6MhB4s7TgtyPqMKZUFwydsXcOiw/edit)

### The BCU Discords
- [BCU English Server](https://discord.gg/zvqamBW)
- [至BCU中文伺服器](https://discord.gg/uyyarVR)
- [BCU 한국 서버 링크](https://discord.gg/fZZnZsw)
- [日本語BCU招待リンク](https://discord.gg/kBB9Qyv3E9)
