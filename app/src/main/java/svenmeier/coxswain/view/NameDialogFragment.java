package svenmeier.coxswain.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.EditText;

import propoid.core.Propoid;
import propoid.db.Reference;
import svenmeier.coxswain.Gym;
import svenmeier.coxswain.R;
import svenmeier.coxswain.gym.Program;


/**
 */
public class NameDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Program program = Gym.instance(getActivity()).getProgram(Reference.<Program>from(getArguments()));

        final EditText editText = new EditText(getActivity());
        editText.setText(program.name.get());

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.action_rename)
                .setView(editText)
                .setPositiveButton(R.string.action_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                program.name.set(editText.getText().toString());

                                Utils.getParent(NameDialogFragment.this, Callback.class).changed(program);

                                dismiss();
                            }
                        }
                )
                .create();

        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return alertDialog;
    }

    public static NameDialogFragment create(Program program) {
        NameDialogFragment fragment = new NameDialogFragment();

        fragment.setArguments(new Reference<>(program).set(new Bundle()));

        return fragment;
    }

    public static interface Callback {
        public void changed(Program program);
    }
}