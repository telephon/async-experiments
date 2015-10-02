

// wait for user input
(
async {
	"waiting for boot...".postln;
	s.await.boot;
	s.notify;
	"waiting for Dialog ...".postln;
	Dialog.await.openPanel;
	"... and running a synth".postln;
	a = Synth.await.new(\default);
}
)



// wait for server and group
(
s.quit;
async {
	"waiting for boot...".postln;
	s.await.boot;
	s.notify;
	"waiting for Group ...".postln;
	g = Group.await;
	g.postcs;
	"----".postln;
	g.new(s);
	"... and running the synth".postln;
	a = Synth.await.new(\default, g.object);
	1.wait;
	a.release;

}
)
