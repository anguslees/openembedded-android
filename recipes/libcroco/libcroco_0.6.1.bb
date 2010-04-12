DESCRIPTION = "The Libcroco project is an effort to build a generic Cascading Style Sheet (CSS) parsing and manipulation toolkit"
SECTION = "x11/utils"
DEPENDS = "glib-2.0 libxml2"
LICENSE = "LGPL"
PR = "r3"

inherit gnome

SRC_URI_append = " file://croco.patch;patch=1 "

