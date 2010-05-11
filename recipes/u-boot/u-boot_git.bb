require u-boot.inc
PR ="r49"

FILESPATHPKG =. "u-boot-git:"

SRC_URI = "git://www.denx.de/git/u-boot.git;protocol=git "
SRCREV_davinci-sffsdr = "4b50cd12a3b3c644153c4cf393f4a4c12289e5aa"
SRCREV_akita = "9bf86baaa3b35b25baa2d664e2f7f6cafad689ee"
SRCREV_spitz = "9bf86baaa3b35b25baa2d664e2f7f6cafad689ee"
SRCREV_c7x0 = "9bf86baaa3b35b25baa2d664e2f7f6cafad689ee"
SRCREV_afeb9260 = "6b8edfde22acc574b5532e9f086e6a7287a9bc78"
SRCREV_afeb9260-180 = "6b8edfde22acc574b5532e9f086e6a7287a9bc78"
SRCREV_palmpre = "6b8edfde22acc574b5532e9f086e6a7287a9bc78"
SRCREV_cm-t35 = "3c014f1586d5bfe30dca7549396915c83f31cd30"
SRCREV_mpc8641-hpcn = "f20393c5e787b3776c179d20f82a86bda124d651"
SRCREV_p2020ds = "f20393c5e787b3776c179d20f82a86bda124d651"
SRC_URI_append_afeb9260 = " file://AFEB9260-network-fix.patch;patch=1"
SRC_URI_append_afeb9260-180 = " file://AFEB9260-network-fix.patch;patch=1"
SRC_URI_append_cm-t35 = "file://cm-t35/cm-t35.patch;patch=1"

SRC_URI_beagleboard = "git://www.denx.de/git/u-boot.git;protocol=git \
                       file://0001-OMAP3-enable-i2c-bus-switching-for-Beagle-and-Overo.patch;patch=1 \
                       file://0002-OMAP3-add-board-revision-detection-for-Overo.patch;patch=1 \
                       file://0003-OMAP3-update-Beagle-revision-detection-to-recognize-.patch;patch=1 \
                       file://0004-OMAP3-Set-VAUX2-to-1.8V-for-EHCI-PHY-on-Beagle-Rev-C.patch;patch=1 \
                       file://0005-OMAP3-add-entry-for-rev-3.1.2-check-and-display-max-.patch;patch=1 \
                       file://0006-OMAP3-add-mpurate-boot-arg-for-overo-and-beagle.patch;patch=1 \
                       file://0007-OMAP3-detect-expansion-board-type-version-using-eepr.patch;patch=1 \
                       file://0008-OMAP3-Overo-enable-config-eeprom-to-set-u-boot-env-v.patch;patch=1 \
                       file://0009-OMAP3-Overo-enable-input-on-MMC1_CLK-and-MMC3_CLK-pi.patch;patch=1 \
                       file://0010-OMAP3-Overo-set-CONFIG_SYS_I2C_SPEED-to-400Khz.patch;patch=1 \
                       file://0011-OMAP3-trim-excessively-long-delays-in-i2c-driver.patch;patch=1 \
                       file://0012-OMAP3-Overo-allow-expansion-boards-with-any-vendor-I.patch;patch=1 \
                       file://0013-OMAP3-Overo-change-address-of-expansion-eeprom-to-0x.patch;patch=1 \
                       file://0014-OMAP3-board.c-don-t-attempt-to-set-up-second-RAM-ban.patch;patch=1 \
                       file://0015-OMAP3-mem.c-enhance-the-RAM-test.patch;patch=1 \
                       file://0016-env_nand.c-fail-gracefully-if-no-nand-is-present.patch;patch=1 \
                       file://0017-OMAP3-add-definitions-to-support-sysinfo-cpu-and-cpu.patch;patch=1 \
                       file://0018-OMAP3-sys_info-update-cpu-detection-for-36XX-37XX.patch;patch=1 \
                       file://0019-OMAP3-clocks-update-clock-setup-for-36XX-37XX.patch;patch=1 \
                       file://0020-OMAP3-beagle-add-support-for-Beagle-xM.patch;patch=1 \
                       file://0021-OMAP3-Beagle-Overo-remove-omapfb.debug-y-from-defaul.patch;patch=1 \
                       file://0022-OMAP3-beagle-implement-expansionboard-detection-base.patch;patch=1 \
                       file://0023-beagleboard-display-message-about-I2C-errors-being-e.patch;patch=1 \
                       file://0024-beagleboard-fix-TCT-expansionboard-IDs.patch;patch=1 \
                       file://0025-Add-DSS-driver-for-OMAP3.patch;patch=1 \
                       file://0026-Enable-DSS-driver-for-Beagle.patch;patch=1 \
                       file://0027-beagleboardXM-don-t-set-mpurate-on-xM-in-bootargs.patch;patch=1 \
                       file://0028-OMAP3-fix-and-clean-up-L2-cache-enable-disable-funct.patch;patch=1 \
                       file://0029-OMAP3-convert-setup_auxcr-to-pure-asm.patch;patch=1 \
                       file://0030-OMAP3-apply-Cortex-A8-errata-workarounds-only-on-aff.patch;patch=1 \
                       file://0031-OMAP3-beagle-add-more-expansionboards-based-on-http-.patch;patch=1 \
                       file://0032-OMAP3-beagle-set-mpurate-to-600-for-revB-and-revC1-3.patch;patch=1 \
                       file://0033-OMAP3-beagle-prettify-expansionboard-message-a-bit.patch;patch=1 \
                       file://0034-OMAP3-beagle-add-pinmux-for-Tincantools-Trainer-expa.patch;patch=1 \
                       file://0035-OMAP3-Beagle-set-mpurate-to-1000-for-xM.patch;patch=1 \
                       file://0036-OMAP3-Beagle-decrease-bootdelay-to-3-use-VGA-for-def.patch;patch=1 \
                       file://0037-OMAP3-beagle-pass-expansionboard-name-in-bootargs.patch;patch=1 \
                       file://fw_env.config \
"
SRCREV_beagleboard = "ca6e1c136ddb720c3bb2cc043b99f7f06bc46c55"
PV_beagleboard = "2010.03+${PR}+gitr${SRCREV}"

SRCREV_calamari = "533cf3a024947aaf74c16573a6d951cd0c3d0a7d"

PV_calamari = "2009.11+${PR}+gitr${SRCREV}"
SRC_URI_calamari = " \
        git://git.denx.de/u-boot-mpc85xx.git;protocol=git \
	file://0002-cmd_itest.c-fix-pointer-dereferencing.patch;patch=1 \
	file://0001-cmd_i2c.c-reduced-subaddress-length-to-3-bytes.patch;patch=1 \
	file://0002-cmd_bootm.c-made-subcommand-array-static.patch;patch=1 \
	file://0003-cmd_i2c.c-reworked-subcommand-handling.patch;patch=1 \
	file://0004-cmd_i2c.c-sorted-commands-alphabetically.patch;patch=1 \
	file://0005-cmd_i2c.c-added-i2c-read-to-memory-function.patch;patch=1 \
	file://0007-cmd_setexpr-allow-memory-addresses-and-env-vars-in-e.patch;patch=1 \
        "

UBOOT_MACHINE_calamari = "MPC8536DS_config"

SRC_URI_omap3-touchbook = "git://gitorious.org/u-boot-omap3/mainline.git;branch=omap3-dev;protocol=git \
                 file://fw_env.config \
                 file://dss2.patch;patch=1 \
                 file://600mhz.patch;patch=1 \
                 file://new-pinmux.patch;patch=1 \
                 file://spi3.patch;patch=1 \
                 file://spi4.patch;patch=1 \
                 file://headphone.patch;patch=1 \
                 file://power.patch;patch=1 \
                 file://ai-logo.patch;patch=1 \
                 file://mmcinit.patch;patch=1 \
                 file://touchbook-config.patch;patch=1 \
"
SRCREV_omap3-touchbook = "d363f9cb0918a1b6b92e2e20d01543d0c4f53274"
PV_omap3-touchbook = "2009.05+${PR}+gitr${SRCREV}"


SRC_URI_omap3evm = "git://gitorious.org/u-boot-omap3/mainline.git;branch=omap3-dev;protocol=git"
SRCREV_omap3evm = "2dea1db2a3b7c12ed70bbf8ee50755089c5e5170"
PV_omap3evm = "2009.03+${PR}+gitr${SRCREV}"

SRC_URI_dm3730-am3715-evm = "git://arago-project.org/git/projects/u-boot-omap3.git;protocol=git"
# This tag is v2009.11_OMAPPSP_03.00.00.05
SRCREV_dm3730-am3715-evm = "9df15c53c9a9bc1ec9c68c33821c50dc26797d6c"
PV_dm3730-am3715-evm = "2009.11+${PR}+gitr${SRCREV}"

SRCREV_am3517-evm = "e60beb13cf0"
SRC_URI_append_am3517-evm = " \
file://omap3evm/0001-Changes-for-making-a-NAND-build.patch;patch=1 \
file://omap3evm/0002-Fix-for-NFS-boot-for-OMAP3-EVM.patch;patch=1 \
file://omap3evm/0003-OMAP3-timer-handling-to-1ms-tick-and-CONFIG_SYS_HZ-t.patch;patch=1 \
file://omap3evm/0004-Reverse-patch-for-NFS-boot-to-fix-comments-provided.patch;patch=1 \
file://omap3evm/0005-SMC911x-driver-fixed-for-NFS-boot.patch;patch=1 \
file://omap3evm/0006-Added-OMAP3517-3505-support.patch;patch=1 \
file://omap3evm/0007-OMAP3517TEB-validated-on-OMAP3517TEB-board.patch;patch=1 \
file://omap3evm/0008-OMAP3517PRE-ALPHA-validated-on-OMAP3517PRE_ALPHA-bo.patch;patch=1 \
file://omap3evm/0009-OMAP3517PRE-ALPHA-DDR-size-issue-fixed.patch;patch=1 \
file://omap3evm/0010-OMAP3517PRE-ALPHA-Mux-configuration-for-MMC-CD-and.patch;patch=1 \
file://omap3evm/0011-Ethernet-driver-functional-no-need-for-time-delay.patch;patch=1 \
file://omap3evm/0012-EMAC-driver-Implement-GPIO-driven-PHY-reset.patch;patch=1 \
file://omap3evm/0013-Cleaned-up-during-EVM-hang-issue.patch;patch=1 \
file://omap3evm/0014-EMAC-driver-cleanup-removed-debug-prints.patch;patch=1 \
file://omap3evm/0015-EMAC-driver-Check-for-link-status-in-packet-send-lo.patch;patch=1 \
file://omap3evm/0016-Config-option-and-name-changed-to-omap3517_evm.patch;patch=1 \
"
PV_am3517-evm = "2009.03+${PR}+gitr${SRCREV}"

SRC_URI_omapzoom = "git://www.sakoman.net/git/u-boot-omap3.git;branch=omap3-dev;protocol=git"
SRCREV_omapzoom = "d691b424f1f5bf7eea3a4131dfc578d272e8f335"
PV_omapzoom = "2009.01+${PR}+gitr${SRCREV}"

SRC_URI_omapzoom2 = "git://dev.omapzoom.org/pub/scm/bootloader/u-boot.git;branch=master;protocol=git \
                     file://0001-OMAP3-set-L1NEON-bit-in-aux-control-register.patch;patch=1"
SRCREV_omapzoom2 = "78e778e0ea884306841c6499851a1e35177d81d0"
PV_omapzoom2 = "1.1.4+${PR}+gitr${SRCREV}"
PE_omapzoom2 = "1"

do_compile_omapzoom2 () {
        unset LDFLAGS
        unset CFLAGS
        unset CPPFLAGS
        oe_runmake ${UBOOT_MACHINE}
        oe_runmake all
        oe_runmake tools
}

SRC_URI_omapzoom36x = "git://dev.omapzoom.org/pub/scm/bootloader/u-boot.git;branch=master;protocol=git"
SRCREV_omapzoom36x = "ab45d2a787a9674bed30542139175d8e090e0749"
PV_omapzoom36x = "1.1.4+${PR}+gitr${SRCREV}"
PE_omapzoom36x = "1"

do_compile_omapzoom36x () {
        unset LDFLAGS
        unset CFLAGS
        unset CPPFLAGS
        oe_runmake ${UBOOT_MACHINE}
        oe_runmake all
        oe_runmake tools
}

SRC_URI_overo = "git://gitorious.org/u-boot-omap3/mainline.git;branch=omap3-dev;protocol=git \
                 file://fw-env.patch;patch=1 \
                 file://dss2.patch;patch=1 \
"
SRCREV_overo = "2dea1db2a3b7c12ed70bbf8ee50755089c5e5170"
PV_overo = "2009.03+${PR}+gitr${SRCREV}"

# DaVinci dm355-evm/dm365-evm/dm6446-evm/dm6467-evm/dm6467t-evm - PSP 3.1.0/3.2.0 (build 35)

SRC_URI_dm355-evm    = "git://arago-project.org/git/projects/u-boot-davinci.git;protocol=git;branch=u-boot-davinci-2009.06"
SRCREV_dm355-evm     = "ea7387c9511ac92a46d3d147adffe36f868820e4"
PV_dm355-evm         = "2009.05+2009.06-rc0+${PR}+gitr${SRCREV}"
PE_dm355-evm         = "1"

SRC_URI_dm365-evm    = "git://arago-project.org/git/projects/u-boot-davinci.git;protocol=git;branch=u-boot-davinci-2009.06"
SRCREV_dm365-evm     = "ea7387c9511ac92a46d3d147adffe36f868820e4"
PV_dm365-evm         = "2009.05+2009.06-rc0+${PR}+gitr${SRCREV}"
PE_dm365-evm         = "1"

SRC_URI_dm6446-evm   = "git://arago-project.org/git/projects/u-boot-davinci.git;protocol=git;branch=u-boot-davinci-2009.06"
SRCREV_dm6446-evm    = "ea7387c9511ac92a46d3d147adffe36f868820e4"
PV_dm6446-evm        = "2009.05+2009.06-rc0+${PR}+gitr${SRCREV}"
PE_dm6446-evm        = "1"

SRC_URI_dm6467-evm   = "git://arago-project.org/git/projects/u-boot-dm646x.git;protocol=git"
SRCREV_dm6467-evm    = "98b31e3aae3e3fb772f8d06c18ccdd6265aa0d38"
PV_dm6467-evm        = "2009.08+${PR}+gitr${SRCREV}"

SRC_URI_dm6467t-evm  = "git://arago-project.org/git/projects/u-boot-dm646x.git;protocol=git"
SRCREV_dm6467t-evm   = "98b31e3aae3e3fb772f8d06c18ccdd6265aa0d38"
PV_dm6467t-evm       = "2009.08+${PR}+gitr${SRCREV}"

# OMAPL1 da380-omapl137/da850-omapl138-evm - PSP 3.20.0.11

SRC_URI_da830-omapl137-evm = "git://arago-project.org/git/projects/u-boot-omapl1.git;protocol=git"
SRCREV_da830-omapl137-evm  = "5f16b8551b125f16cd8d58f278cb25b94272fd9f"
PV_da830-omapl137-evm      = "2009.11+${PR}+gitr${SRCREV}"

SRC_URI_da850-omapl138-evm = "git://arago-project.org/git/projects/u-boot-omapl1.git;protocol=git"
SRCREV_da850-omapl138-evm  = "5f16b8551b125f16cd8d58f278cb25b94272fd9f"
PV_da850-omapl138-evm      = "2009.11+${PR}+gitr${SRCREV}"

# hawkboard - master branch (hawk still .07beta)

SRC_URI_hawkboard          = "git://arago-project.org/git/people/sekhar/u-boot-omapl1.git;protocol=git;branch=master"
SRCREV_hawkboard           = "0d291f2f255e6d66a78b3dc2445362a96ae39a57"
PV_hawkboard               = "2009.08+gitr${SRCREV}"

SRC_URI_dm355-leopard = "git://www.denx.de/git/u-boot-arm.git;protocol=git;branch=master \
"
SRCREV_dm355-leopard = "d650da2dd4af99967aabc43cccbd8f160eb4cea6"
PV_dm355-leopard = "2009.05+2010.03-rc1+gitr${SRCREV}"

SRC_URI_neuros-osd2 = "git://github.com/neuros/u-boot.git;protocol=git;branch=neuros"
SRCREV_neuros-osd2 = "8de979d346624c0e4cfe2e5c0f08ce20ca4b5d14"

SRC_URI_sequoia = "git://www.denx.de/git/u-boot.git;protocol=git"
SRCREV_sequoa = "cf3b41e0c1111dbb865b6e34e9f3c3d3145a6093"

SRC_URI_sequoia = "git://www.denx.de/git/u-boot.git;protocol=git;tag=cf3b41e0c1111dbb865b6e34e9f3c3d3145a6093 "

SRC_URI_mini2440 = "git://repo.or.cz/u-boot-openmoko/mini2440.git;protocol=git;branch=dev-mini2440-stable"
SRCREV_mini2440 = "3516c35fb777ca959e5cadf2156a792ca10e1cff"

SRC_URI_micro2440 = "git://repo.or.cz/u-boot-openmoko/mini2440.git;protocol=git;branch=dev-mini2440-stable"
SRCREV_micro2440 = "3516c35fb777ca959e5cadf2156a792ca10e1cff"

SRC_URI_neuros-osd2 += "file://Makefile-fix.patch;patch=1"
SRC_URI_append_akita = "file://pdaXrom-u-boot.patch;patch=1 \
                        file://uboot-eabi-fix-HACK2.patch;patch=1 \
                        file://akita-standard-partitioning.patch;patch=1 \
                       "
SRC_URI_append_spitz = "file://pdaXrom-u-boot.patch;patch=1 \
                        file://uboot-eabi-fix-HACK2.patch;patch=1 \
                        file://spitz-standard-partitioning.patch;patch=1 \
                       "
SRC_URI_append_c7x0 = "file://pdaXrom-u-boot.patch;patch=1 \
                       file://uboot-eabi-fix-HACK2.patch;patch=1 \
                       file://corgi-standard-partitioning.patch;patch=1 \
                       "
SRC_URI_sheevaplug = "git://git.denx.de/u-boot-marvell.git;protocol=git;branch=testing"
SRCREV_sheevaplug = "119b9942da2e450d4e525fc004208dd7f7d062e0"

SRC_URI_xilinx-ml507 = "git://git.xilinx.com/u-boot-xlnx.git;protocol=git"
SRCREV_xilinx-ml507 = "26e999650cf77c16f33c580abaadab2532f5e8b2"

S = "${WORKDIR}/git"


do_configure_prepend_akita() {
        sed -i s:ROOT_FLASH_SIZE:${ROOT_FLASH_SIZE}:g ${S}/include/configs/akita.h
}

do_configure_prepend_spitz() {
        sed -i s:ROOT_FLASH_SIZE:${ROOT_FLASH_SIZE}:g ${S}/include/configs/akita.h
}

do_configure_prepend_c7x0() {
        sed -i s:ROOT_FLASH_SIZE:${ROOT_FLASH_SIZE}:g ${S}/include/configs/corgi.h
}

do_compile_append_sheevaplug() {
	oe_runmake u-boot.kwb
}

UBOOT_IMAGE_sheevaplug = "u-boot-${MACHINE}-${PV}-${PR}.kwb"
UBOOT_BINARY_sheevaplug = "u-boot.kwb"
UBOOT_SYMLINK_sheevaplug ?= "u-boot-${MACHINE}.kwb"

do_deploy_prepend_mini2440() {
	cp ${S}/u-boot-nand16k.bin ${S}/u-boot.bin
}

do_deploy_prepend_micro2440() {
	cp ${S}/u-boot-nand16k.bin ${S}/u-boot.bin
}

do_configure_prepend_xilinx-ml507() {
if [ -e "${XILINX_BSP_PATH}/ppc440_0/include/xparameters.h" ]; then
    cp ${XILINX_BSP_PATH}/ppc440_0/include/xparameters.h \
    ${S}/board/xilinx/ml507
    echo "#define XPAR_PLB_CLOCK_FREQ_HZ XPAR_CPU_PPC440_MPLB_FREQ_HZ
#define XPAR_CORE_CLOCK_FREQ_HZ XPAR_CPU_PPC440_CORE_CLOCK_FREQ_HZ
#define XPAR_PCI_0_CLOCK_FREQ_HZ    0" >> ${S}/board/xilinx/ml507/xparameters.h
fi
}

do_deploy_prepend_xilinx-ml507() {
if [ -d "${XILINX_BSP_PATH}" ]; then
    install ${S}/u-boot ${XILINX_BSP_PATH}
fi
}
