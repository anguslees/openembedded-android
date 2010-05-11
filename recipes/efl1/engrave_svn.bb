DESCRIPTION = "Engrave is an Edje Editing Library"
LICENSE = "MIT"
# also requires yacc and lex on host
DEPENDS = "evas ecore"
PV = "0.0.0+svnr${SRCPV}"
PR = "r1"
SRCREV = "${EFL_SRCREV}"

inherit efl

SRC_URI = "svn://svn.enlightenment.org/svn/e/OLD;module=${PN};proto=http"
