inherit gpe

DESCRIPTION = "GPE session startup scripts"
LICENSE = "GPL"
SECTION = "gpe"
PRIORITY = "optional"
RDEPENDS_${PN} = "matchbox-panel matchbox-desktop  matchbox-common gpe-session-starter xtscal gpe-question matchbox-applet-inputmanager xmodmap xdpyinfo xserver-common ipaq-sleep"
# more rdepends: keylaunch apmd blueprobe
DEPENDS = "matchbox-wm matchbox-panel xtscal gpe-question matchbox-applet-inputmanager xmodmap xdpyinfo xserver-common ipaq-sleep"

SRC_URI += "file://matchbox-session \
	file://disable-composite.xsettings"

PR = "r0"

#apply a patch to set the fontsize for bigdpi (200+) devices to 5
SRC_URI_append_hx4700 = " file://highdpifontfix.patch"
SRC_URI_append_spitz = " file://highdpifontfix.patch"
SRC_URI_append_akita = " file://highdpifontfix.patch"
SRC_URI_append_c7x0 = " file://highdpifontfix.patch"
SRC_URI_append_nokia770 = " file://highdpifontfix.patch"


do_configure_append_angstrom() {
	sed -i s:Default:Clearlooks:g X11/xsettings.default
	sed -i s:Industrial:Clearlooks:g X11/xsettings.default
}

export CURSOR_HIDE = '${@base_contains("MACHINE_FEATURES","touchscreen","-use_cursor no","-use_cursor yes",d)}'

do_install_append() {
	install -d ${D}${sysconfdir}/gpe/xsettings-default.d
	if [ "${GUI_MACHINE_CLASS}" != "bigscreen" ]; then
		echo "Gtk/ToolbarStyle:S:icons" > ${D}${sysconfdir}/gpe/xsettings-default.d/toolbar
	fi
	install -d ${D}${sysconfdir}/matchbox
	install ${WORKDIR}/matchbox-session ${D}${sysconfdir}/matchbox/session
        printf "exec matchbox-window-manager ${CURSOR_HIDE} \$@ \n" >> ${D}${sysconfdir}/matchbox/session	


	install -d ${D}${sysconfdir}/gpe/xsettings-default.d
	install -m 0644 ${WORKDIR}/disable-composite.xsettings ${D}${sysconfdir}/gpe/xsettings-default.d/disable-composite

	mv ${D}${bindir}/gpe-logout.sh ${D}${bindir}/gpe-logout.sh.matchbox
}

pkg_postinst_${PN}() {
	update-alternatives --install ${bindir}/gpe-logout.sh gpe-logout.sh ${bindir}/gpe-logout.sh.matchbox 10
}

pkg_postrm_${PN}() {
       update-alternatives --remove gpe-logout.sh ${bindir}/gpe-logout.sh.matchbox
}

# This makes use of GUI_MACHINE_CLASS, so set PACKAGE_ARCH appropriately
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI[md5sum] = "af6941561f0ef676b4536531cd4c40bf"
SRC_URI[sha256sum] = "f0cc9809776f77649287e88455657d47e804b11296c0f9f503ea0b1603b2e377"
