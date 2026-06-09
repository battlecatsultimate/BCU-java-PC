# Battle Cats Ultimate
###### A fan-made program featuring data and animations in The Battle Cats by PONOS, along with custom content.
###### Last Written: July 21st, 2024

## Installing BCU Version 5 for Windows, macOS, or Linux

1. Download and install [the JRE](https://www.java.com/en/download/manual.jsp) that corresponds to your operating system.
    - For macOS users, you might want to download [JDK 8](https://drive.google.com/file/d/1tX-LQygjt3w0x3uQnyHBC2O-uePUQrPn/view?usp=sharing) as well in the case that BCU fails to work properly, especially if JOGL is enabled.
2. Download [BCU-Initializer.jar](https://github.com/battlecatsultimate/bcu-assets/raw/master/jar/BCU-Initializer.jar)
3. Move the jar to a new, empty folder, then run the jar.

## Frequently Asked Questions
### How do I create custom content? / How do I animate?
Refer to the links below with the label "BCU Tutorial" under the **Links & Resources** section.

### Common Issues/Errors
If none of these apply to your situation, you can report it through GitHub Issues or the BCU English Discord server, linked below. Make sure to include the most recent error log, which should be the newest .log file in the `log` folder.
- If you're able to open BCU, you can also find the latest log file through the `Error Logs` button in the main menu.

#### All Platforms
- *Another BCU is running or last BCU didn't close properly*
    - If the last BCU crashed, this will be the default message.
    - It's advised to avoid running two BCU instances at the same time, as well as allowing BCU to close properly.
    - If there was a real problem that seemed to have caused the crash, follow the above instructions to report the problem.
- *Not enough memory*
    - Open up BCU again. In the main menu, If the number on the top left says `# / 247 MB` and your computer is 64-bit, you installed the wrong Java. Install 64-bit Java instead.
    - If the above fails, you may need to use run.bat, which is available in the BCU Discord server, linked below.

#### Windows Issues and Warnings

Don't install `Windows Online` installer if your OS type is 64-bit. You will have to download `Windows Offline (64-bit)` one.
It's fine to install `Windows Online` or `Windows Offline` if your OS type is 32-bit. If you install incorrect JRE, BCU will have memory space issue.

**Do not run BCU with `Open with Java` option!** This will connect BCU with `system32` folder, and BCU will try to create subfolders in there.

Below is issue related with Windows that commonly happens.

- Windows open jar file with incorrect program such as WinRAR, etc
    - First of all, do not extract jar file. Jar files are executable file like exe, not something supposed to be extracted.
    - Follow steps below to change program connection.
    1. Click BCU jar file once
    2. Put mouse on jar file, and right click it
    3. Click `Properties`
    4. Click `Change...` next to `Open With`
    5. Program list will appear, select Java
    6. Close properties windows by clicking `Apply` and `OK`

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
    - NOTE: As of July 2024, this should no longer be occurring. If this is something you are experiencing, please report it through either GitHub Issues or in the BCU English Discord server!
  
#### Linux Issues
Most common issue on linux is making sure the correct java version is used when launching BCU. Depending on your distro, you can swap your default java very easy. The current java version required for BCU is java 8.
- For Arch Linux distros, use `archlinux-java status` to check your current default java. If it's incorrect, use `archlinux-java set java-8-openjdk` to swap to java 8 as default. You may need to run this command with sudo.
- For Debian/Ubuntu Linux distros, use `sudo update-alternatives --config java`. It will bring up all installed versions of java with the current active one marked and provides a dialog to help switch.
- For Fedora Linux distros, use `sudo alternatives --config java`. It will bring up all installed versions of java with the current active one marked and provide a dialog to help switch.

If you have trouble launching BCU via Linux's GUI, open terminal and navagate to the folder where the bcu jar file is and use `java -jar nameofbcu.jar` to launch it.

## Links & Resources

- [BCU Tutorial - Animating](https://docs.google.com/document/d/1X2UZGEJzkSTXjgvozQRgKoHSjCpsHt4Eh94sllPu8JQ/edit?usp=sharing)
- [BCU Tutorial - Custom Content](https://docs.google.com/document/d/1k682-1xNnHH3Z4sq6MhB4s7TgtyPqMKZUFwydsXcOiw/edit)

### The BCU Discords
- [BCU English Server](https://discord.gg/zvqamBW)
- [至BCU中文伺服器](https://discord.gg/uyyarVR)
- [BCU 한국 서버 링크](https://discord.gg/fZZnZsw)
- [日本語BCU招待リンク](https://discord.gg/kBB9Qyv3E9)
